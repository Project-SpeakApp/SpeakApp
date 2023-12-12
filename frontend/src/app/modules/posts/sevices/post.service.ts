import {Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddPost} from "../../../shared/types/posts/add-post.model";
import {finalize, Observable, tap} from "rxjs";
import {AlertService} from "../../../shared/services/alert.service";
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient, private alertService: AlertService) {

  }

  isLoading = signal(false);


  addPost(model: AddPost, userId: string): Observable<void> {
    this.isLoading.set(true);
    const headers = new HttpHeaders().set('UserId', userId);
    new Promise(resolve => setTimeout(resolve, 5000));

    return this.http.post<void>('http://localhost:8082/api/posts', model, {headers}).pipe(
      finalize( () => {
        this.isLoading.set(false);
        }),
      tap(
        (data) => {console.log(data);},
        (error) => {this.alertService.showAlert(error, 'error')},
      )
    );

  }
}
