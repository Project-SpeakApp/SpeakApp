import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import UserPreviewPage from "../../../shared/types/profiles/user-preview-page";

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(private http: HttpClient) { }

  getFriends(userId: string, page: number, size: number): Observable<UserPreviewPage> {
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());

    return this.http.get<UserPreviewPage>(`http://localhost:8080/api/users/friends/of-user/${userId}`, {params});
  }
}
