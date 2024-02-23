import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AddPost} from "../../../shared/types/posts/add-post.model";
import {PostGetResponse} from "../../../shared/types/posts/post-get-response.model";
import {finalize, Observable, tap} from "rxjs";
import {AlertService} from "../../../shared/services/alert.service";
import {PostGet} from "../../../shared/types/posts/post-get.model";
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient, private alertService: AlertService) {

  }

  updatePost(postId: string, model: AddPost, userId: string): Observable<PostGet> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.put<PostGet>(`http://localhost:8080/api/posts/${postId}`, model, { headers }).pipe(
      finalize( () => {
        }
      ),
      tap(
        (data) => console.log(data),
        (error)=> {this.alertService.showAlert('Something went wrong...', 'error'), console.log(error)}
      )
    );
  }

  deletePost(postId: string, userId: string): Observable<void> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.delete<void>(`http://localhost:8080/api/posts/${postId}`, { headers }).pipe(
      finalize( () => {
      }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error'); console.log(error)},
      )

    );
  }

  addPost(model: AddPost, userId: string): Observable<PostGet> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.post<PostGet>('http://localhost:8080/api/posts', model, {headers}).pipe(
      finalize( () => {
        }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert(error, 'error')},
      )
    );

  }

  getPosts(userId: string, page: number, size: number): Observable<PostGetResponse> {
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());
    return this.http.get<PostGetResponse>('http://localhost:8080/api/posts', {headers, params}).pipe(
      finalize( () => {
      }),
      tap(
        (data) => { console.log(data)},
        (err) => {this.alertService.showAlert('Something went wrong while getting posts', 'error');}
      )
    );

  }





}
