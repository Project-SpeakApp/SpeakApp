import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddPost} from "../../../shared/types/posts/add-post.model";
import {Observable} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {

  }

  addPost(model: AddPost, userId: string): Observable<void> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.post<void>('http://localhost:8082/api/posts', model, {headers});
  }
}
