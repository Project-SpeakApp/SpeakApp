import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {find, Observable, Subscription} from "rxjs";
import UserPreviewPage from "../../../shared/types/profiles/user-preview-page";
import FriendRequestPage from "../../../shared/types/profiles/FriendRequestPage";
import {convertTypeScriptDiagnostic} from "@angular-devkit/build-angular/src/tools/esbuild/angular/diagnostics";

@Injectable({
  providedIn: 'root'
})
export class FriendsService implements OnDestroy{

  sub: Subscription = new Subscription();

  constructor(private http: HttpClient) { }

  getFriends(userId: string, page: number, size: number): Observable<UserPreviewPage> {
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());

    return this.http.get<UserPreviewPage>(`http://localhost:8080/api/users/friends/of-user/${userId}`, {params});
  }

  getFriendRequests(page: number, size: number): Observable<FriendRequestPage> {
    let params = new HttpParams();
    params = params.set('pageNumber', page.toString()).set('pageSize', size.toString());

    return this.http.get<FriendRequestPage>(`http://localhost:8080/api/users/friends/requests`, {params});
  }

  sendFriendRequest(userId: string): Observable<void> {
    return this.http.post<void>(`http://localhost:8080/api/users/friends/send-request/${userId}`, null);
  }

  acceptFriendRequest(id: string): Observable<void> {
    return this.http.post<void>(`http://localhost:8080/api/users/friends/accept-request/${id}`, null);
  }

  rejectFriendRequest(id: string): Observable<void> {
    return this.http.post<void>(`http://localhost:8080/api/users/friends/reject-request/${id}`, null);
  }

  unfriend(userId: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/users/friends/${userId}`);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
