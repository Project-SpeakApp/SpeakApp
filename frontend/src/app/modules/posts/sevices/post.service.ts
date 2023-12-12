import {Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AddPost} from "../../../shared/types/posts/add-post.model";
import {Observable} from "rxjs";
import {PostGetResponse} from "../../../shared/types/posts/post-get-response.model";
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {

  }

  isLoading = signal(false);


  addPost(model: AddPost, userId: string): Observable<void> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.post<void>('http://localhost:8082/api/posts', model, {headers});
  }

  getPosts(userId: string, page: number, size: number): Observable<PostGetResponse> {
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams();
    params = params.set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>('http://localhost:8082/api/posts', {headers, params});

  }

  deletePost(postId: string, userId: string): Observable<void> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.delete<void>(`http://localhost:8082/api/posts/${postId}`, { headers });
  }



}
