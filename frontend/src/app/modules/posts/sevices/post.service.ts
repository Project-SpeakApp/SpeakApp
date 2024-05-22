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
    return this.http.put<PostGet>(`https://localhost:4200/api/posts/${postId}`, model);
  }

  deletePost(postId: string): Observable<void> {
    return this.http.delete<void>(`https://localhost:4200/api/posts/${postId}`);
  }

  addPost(model: AddPost): Observable<PostGet> {
    return this.http.post<PostGet>('https://localhost:4200/api/posts', model);
  }

  getPosts(page: number, size: number, favouritePosts?: boolean, userId?: string): Observable<PostGetResponse> {
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());
    return this.http.get<PostGetResponse>(
      favouritePosts ? 'https://localhost:4200/api/posts/favouriteList' :
      userId ? `https://localhost:4200/api/posts/by-user/${userId}` : 'https://localhost:4200/api/posts',
      {params});
  }

  saveToFavourites(postId: string): Observable<void> {
    return this.http.post<void>(`https://localhost:4200/api/posts/favouriteList`, { postId: postId });
  }

  removeFromFavourites(postId: string): Observable<void> {
    return this.http.delete<void>(`https://localhost:4200/api/posts/favouriteList`, { body: {postId: postId}});
  }
}
