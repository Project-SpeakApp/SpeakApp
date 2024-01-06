import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { finalize, tap } from 'rxjs';
import { AlertService } from 'src/app/shared/services/alert.service';
import { AuthService } from 'src/app/shared/services/auth.service';
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';

@Injectable({
  providedIn: 'root',
})
export class ReactionService {
  constructor(
    private http: HttpClient,
    private alertService: AlertService,
    private authService: AuthService,
  ) {}

  isLoading = signal(false);

  public upsertReactionToPost(
    postId: string,
    reactionType: ReactionType,
    currentReactionType: ReactionType,
  ) {
    this.isLoading.set(true);

    const reactionToSend = reactionType === currentReactionType ? null : reactionType;
    const headers = new HttpHeaders().set('UserId',this.authService.state().userId,);

    return this.http
      .put<void>(`http://localhost:8082/api/posts/reactions/${postId}?reactionType=${reactionToSend}`,{}, {headers})
      .pipe(
        finalize(() => { this.isLoading.set(false) }),
        tap(
          (data) => { console.log(data); },
          (error) => { this.alertService.showAlert(error, 'error'); },
        ),
      );
  }
}
