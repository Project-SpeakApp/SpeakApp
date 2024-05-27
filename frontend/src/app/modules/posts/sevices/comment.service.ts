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

  getComments(postId: string, firstComment: number, lastComment: number, sortBy: string, sortDirection: string): Observable<CommentGetListModel> {
    let params = new HttpParams().set('postId', postId).set('firstComment', firstComment).set('lastComment', lastComment-1).set('sortBy', sortBy ).set('sortDirection', sortDirection);
    return this.http.get<CommentGetListModel>('https://localhost:4443/api/comments', {params});
  }

  deleteComment(commentId: string): Observable<void> {
    return this.http.delete<void>(`https://localhost:4443/api/comments/${commentId}`);
  }

  addComment(model: AddComment): Observable<CommentGetModel> {
      return this.http.post<CommentGetModel>('https://localhost:4443/api/comments', model);
  }

  updateComment(model: UpdateCommentModel, commentId: string) : Observable<CommentGetModel> {
    return this.http.put<CommentGetModel>(`https://localhost:4443/api/comments/${commentId}`, model);
  }
}
