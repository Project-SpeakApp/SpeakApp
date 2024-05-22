import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import { MessageGetDTO } from '../../../shared/types/chat/message-get-DTO.model';
import { MessagePrivateCreateDTO } from '../../../shared/types/chat/message-private-create-dto.model';
import { Stomp } from "@stomp/stompjs";
import * as SockJS from "sockjs-client";
import {ChatPreviewsResponse} from "../../../shared/types/chat/chat-preview-response.model";
import {ConversationHistoryGetDtoModel} from "../../../shared/types/chat/conversation-history-get-dto.model";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: any;
  public messageReceived = new Subject<MessageGetDTO>();


  constructor(private http: HttpClient) { }

  public connect(userId: string): void {
    const socket = new SockJS('http://localhost:8084/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      console.log(userId);
      this.stompClient.subscribe(`/chat/${userId}`, (message: any) => {
        const messageData: MessageGetDTO = JSON.parse(message.body);
        this.messageReceived.next(messageData);
      });
    }, this.onError);
  }

  public disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
      console.log('Disconnected');
    }
  }

  public sendMessage(messageDTO: MessagePrivateCreateDTO): Observable<any> {
    return new Observable((subscriber) => {
      if (this.stompClient && this.stompClient.connected) {
        console.log(messageDTO);
        this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(messageDTO));
        subscriber.next({ message: 'Message sent successfully' });
        subscriber.complete();
      } else {
        subscriber.error('WebSocket connection is not established.');
      }
    });
  }

  private onError(error: string): void {
    console.error('Error in WebSocket connection:', error);
  }

  getMessages(conversationId: string, pageNumber: number = 0, pageSize: number = 5): Observable<ConversationHistoryGetDtoModel> {
    let params = new HttpParams().set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<ConversationHistoryGetDtoModel>(`http://localhost:8080/api/chat/${conversationId}`, {params});
  }

  getChatPreview(pageNumber: number = 0, pageSize: number = 10): Observable<ChatPreviewsResponse> {
    let params = new HttpParams();
    params = params.set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<ChatPreviewsResponse>('http://localhost:8080/api/chat/chatpreview', {params});
  }
}
