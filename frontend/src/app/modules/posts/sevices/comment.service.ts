import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {CommentGetListModel} from "../../../shared/types/posts/comment-get-list.model";
import {AddComment} from "../../../shared/types/posts/add-comment.model";
import {CommentGetModel} from "../../../shared/types/posts/comment-get.model";
import {UpdateCommentModel} from "../../../shared/types/posts/update-comment.model";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  getComments(postId: string, currentPage: number, pageSize: number, sortBy: string, sortDirection: string): Observable<CommentGetListModel> {
    let params = new HttpParams().set('postId', postId).set('pageNumber', currentPage).set('pageSize', pageSize).set('sortBy', sortBy ).set('sortDirection', sortDirection);
    return this.http.get<CommentGetListModel>('http://localhost:8080/api/comments', {params});
  }

  deleteComment(commentId: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/comments/${commentId}`);
  }

  addComment(model: AddComment): Observable<CommentGetModel> {
      return this.http.post<CommentGetModel>('http://localhost:8080/api/comments', model);
  }

  updateComment(model: UpdateCommentModel, commentId: string) : Observable<CommentGetModel> {
    return this.http.put<CommentGetModel>(`http://localhost:8080/api/comments/${commentId}`, model);
  }
}
