import {Injectable} from '@angular/core';
import {finalize, Observable, tap} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AlertService} from "../../../shared/services/alert.service";
import {CommentGetListModel} from "../../../shared/types/posts/comment-get-list.model";
import {AddComment} from "../../../shared/types/posts/add-comment.model";
import {CommentGetModel} from "../../../shared/types/posts/comment-get.model";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private alertService: AlertService) { }
  getComments(postId: string, userId: string, currentPage: number, pageSize: number, sortBy: string, sortDirection: string): Observable<CommentGetListModel> {
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams().set('postId', postId).set('pageNumber', currentPage).set('pageSize', pageSize).set('sortBy', sortBy ).set('sortDirection', sortDirection);
    return this.http.get<CommentGetListModel>('http://localhost:8080/api/comments', {headers, params}).pipe(
      finalize( () => {
      }),
      tap (
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error');},

      )
    );
  }

  deleteComment(commentId: string, userId: string): Observable<void> {
    const headers = new HttpHeaders().set('UserId', userId);
    return this.http.delete<void>(`http://localhost:8080/api/comments/${commentId}`, {headers} ).pipe(
      finalize( ()=> {

      }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error'); }
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
