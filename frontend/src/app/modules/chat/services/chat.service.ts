import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {ChatPreviewsResponse} from "../../../shared/types/chat/chat-preview-response.model";
import {MessageGetDTO} from "../../../shared/types/chat/message-get-DTO.model";
import {MessagePrivateCreateDTO} from "../../../shared/types/chat/message-private-create-dto.model";



@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private socket: WebSocket | null = null;
  private messageSubject: Subject<any> = new Subject();

  public messages: Observable<any> = this.messageSubject.asObservable();

  connect(userId: string): void {
    this.socket = new WebSocket(`http://localhost:8084/ws/chat/${userId}`);

    this.socket.onopen = (event) => {
      console.log('Connected: ', event);
    };

    this.socket.onmessage = (event) => {
      this.messageSubject.next(event.data);
    };

    this.socket.onerror = (event) => {
      console.error('WebSocket error: ', event);
    };

    this.socket.onclose = (event) => {
      console.log('Disconnected: ', event);
      this.socket = null;
    };
  }

  disconnect(): void {
    if (this.socket) {
      this.socket.close();
      this.socket = null;
    }
  }

  sendMessage(message: MessagePrivateCreateDTO): void {

  }

  constructor(private http: HttpClient) {
  }

  getMessages(conversationId: string, pageNumber: number = 0, pageSize: number = 5): Observable<MessageGetDTO []> {
    let params = new HttpParams();
    params = params.set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<MessageGetDTO []>(`http://localhost:8080/api/chat/${conversationId}`, {params});
  }

  getChatPreview(pageNumber: number = 0, pageSize: number = 10): Observable<ChatPreviewsResponse> {
    let params = new HttpParams();
    params = params.set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<ChatPreviewsResponse>('http://localhost:8080/api/chat/chatpreview', {params });
  }
}
