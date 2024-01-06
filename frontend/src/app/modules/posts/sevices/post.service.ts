import {Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AddPost} from "../../../shared/types/posts/add-post.model";
import {PostGetResponse} from "../../../shared/types/posts/post-get-response.model";
import {finalize, Observable, tap} from "rxjs";
import {AlertService} from "../../../shared/services/alert.service";
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient, private alertService: AlertService) {

  }

  isLoadingAdd = signal(false);
  isLoadingGet = signal(false);
  isLoadingDelete = signal(false);

  isLoadingUpdate = signal(false);

  updatePost(postId: string, model: AddPost, userId: string): Observable<void> {
    this.isLoadingUpdate.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.put<void>(`http://localhost:8082/api/posts/${postId}`, model, { headers }).pipe(
      finalize( () => {
        this.isLoadingUpdate.set(false);
        }
      ),
      tap(
        (data) => console.log(data),
        (error)=> {this.alertService.showAlert('Something went wrong...', 'error'), console.log(error)}
      )
    );
  }

  addPost(model: AddPost, userId: string): Observable<void> {
    this.isLoadingAdd.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.post<void>('http://localhost:8082/api/posts', model, {headers}).pipe(
      finalize( () => {
        this.isLoadingAdd.set(false);
        }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert(error, 'error')},
      )
    );

  }

  getPosts(userId: string, page: number, size: number): Observable<PostGetResponse> {
    this.isLoadingGet.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());
    return this.http.get<any>('http://localhost:8082/api/posts', {headers, params}).pipe(
      finalize( () => {
        this.isLoadingGet.set(false);
      }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error'); console.log(error)},
      )
    );

  }

  deletePost(postId: string, userId: string): Observable<void> {
    this.isLoadingDelete.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.delete<void>(`http://localhost:8082/api/posts/${postId}`, { headers }).pipe(
      finalize( () => {
        this.isLoadingDelete.set(false);
      }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error'); console.log(error)},
      )

    );
  }
}
