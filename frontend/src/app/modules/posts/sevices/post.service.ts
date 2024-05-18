import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AddPost} from "../../../shared/types/posts/add-post.model";
import {PostGetResponse} from "../../../shared/types/posts/post-get-response.model";
import {Observable} from "rxjs";
import {PostGet} from "../../../shared/types/posts/post-get.model";
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {}

  updatePost(postId: string, model: AddPost): Observable<PostGet> {
    return this.http.put<PostGet>(`http://localhost:8080/api/posts/${postId}`, model);
  }

  deletePost(postId: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/posts/${postId}`);
  }

  addPost(model: AddPost): Observable<PostGet> {
    return this.http.post<PostGet>('http://localhost:8080/api/posts', model);
  }

  getPosts(page: number, size: number, favouritePosts?: boolean, userId?: string): Observable<PostGetResponse> {
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());
    return this.http.get<PostGetResponse>(
      favouritePosts ? 'http://localhost:8080/api/posts/favouriteList' :
      userId ? `http://localhost:8080/api/posts/by-user/${userId}` : 'http://localhost:8080/api/posts',
      {params});
  }
}
