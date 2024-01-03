import {Injectable, signal} from '@angular/core';
import {finalize, Observable, tap} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AlertService} from "../../../shared/services/alert.service";
import {CommentGetModel} from "../../../shared/types/posts/comment-get.model";
import {CommentGetListModel} from "../../../shared/types/posts/comment-get-list.model";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private alertService: AlertService) { }

  isLoadingGet = signal(false);
  getComments(postId: string, userId: string, currentPage: number): Observable<CommentGetListModel> {
    this.isLoadingGet.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams().set('postId', postId);
    params.set('currentPage', currentPage);
    return this.http.get<CommentGetListModel>('http://localhost:8080/api/posts/comments', {headers, params}).pipe( //tez nie jestem pewny czy tak powinien wygladac endpoint
      finalize( () => {
        this.isLoadingGet.set(false);
      }),
      tap (
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert('Something went wrong...', 'error');},

      )
    );
  }
}
