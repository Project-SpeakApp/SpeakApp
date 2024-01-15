import {Injectable, signal} from '@angular/core';
import {BehaviorSubject, finalize, Observable, tap} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AlertService} from "../../../shared/services/alert.service";
import {CommentGetModel} from "../../../shared/types/posts/comment-get.model";
import {CommentGetListModel} from "../../../shared/types/posts/comment-get-list.model";
import {AddComment} from "../../../shared/types/posts/add-comment.model";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private alertService: AlertService) { }
  getComments(postId: string, userId: string, currentPage: number, pageSize: number): Observable<CommentGetListModel> {
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams().set('postId', postId).set('pageNumber', currentPage).set('pageSize', pageSize);
    return this.http.get<CommentGetListModel>('http://localhost:8080/api/comments', {headers, params}).pipe(
      finalize( () => {
      }),
      tap (
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error');},

      )
    );
  }

  addComment(model: AddComment, userId: string): Observable<CommentGetModel> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.post<CommentGetModel>('http://localhost:8080/api/comments', model, {headers}).pipe(
      finalize( () => {

      }),
      tap(
        (data)=> {console.log(data);},
        (error) => {this.alertService.showAlert(error, 'error')},
      )
    );
  }
}
