import {Injectable, signal} from '@angular/core';
import {finalize, Observable, tap} from "rxjs";
import {CommentListGet} from "../../../shared/types/posts/comment-list-get";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {data} from "autoprefixer";
import {AlertService} from "../../../shared/services/alert.service";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private alertService: AlertService) { }

  isLoadingGet = signal(false);
  getComments(postId: string, userId: string): Observable<CommentListGet> {
    this.isLoadingGet.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    let params = new HttpParams().set('postId', postId);
    return this.http.get<any>('http://localhost:8082/api/posts/comments', {headers, params}).pipe(
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
