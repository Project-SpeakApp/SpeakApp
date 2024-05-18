import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { ChatPreviewsResponse } from '../../../shared/types/chat/chat-preview-response.model';
import { MessageGetDTO } from '../../../shared/types/chat/message-get-DTO.model';
import {Stomp} from "@stomp/stompjs";
import * as SockJS from "sockjs-client";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private messageSource = new BehaviorSubject<any>(null);

  constructor(private http: HttpClient) {
  }

  private stompClient: any;

  public connect(): void {
    const socket = new SockJS('http://localhost:8084/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/messages', () => {
        console.log('Got message');
      });

    }, this.onError);
  }

  private onError(error: string) {
    console.error('Error in WebSocket connection:', error);
  }

  public disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
      console.log('Disconnected');
    }
  }

  public sendMessage(message: any): void {
    this.stompClient.send('/app/send', {}, JSON.stringify(message));
  }

  getMessages(conversationId: string, pageNumber: number = 0, pageSize: number = 5): Observable<MessageGetDTO[]> {
    let params = new HttpParams().set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<MessageGetDTO[]>(`http://localhost:8080/api/chat/${conversationId}`, {params});
  }

  getChatPreview(pageNumber: number = 0, pageSize: number = 10): Observable<ChatPreviewsResponse> {
    let params = new HttpParams();
    params = params.set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<ChatPreviewsResponse>('http://localhost:8080/api/chat/chatpreview', {params});
  }
}
