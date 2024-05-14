import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommentGetListModel} from "../../../shared/types/posts/comment-get-list.model";
import {ChatPreviewPageDTO} from "../../../shared/types/chat/chat-preview-page.model";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) { }

  getMessages() {

  }

  sendMessage() {

  }

  getChatPreview(pageNumber: number = 0, pageSize: number = 10): Observable<ChatPreviewPageDTO> {
    let params = new HttpParams();
    params = params.set('pageNumber', pageNumber.toString()).set('pageSize', pageSize.toString());
    return this.http.get<ChatPreviewPageDTO>('http://localhost:8080/api/chat/chatpreview', {params });
  }
}
