import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { finalize, tap } from 'rxjs';
import { AlertService } from 'src/app/shared/services/alert.service';
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';

@Injectable({
  providedIn: 'root',
})
export class ReactionService {
  constructor(
    private http: HttpClient,
    private alertService: AlertService,
  ) {}

  isLoading = signal(false);

  public upsertReaction(
    contentId: string,
    reactionType: ReactionType,
    currentReactionType: ReactionType | null,
    contentType: string = 'post'
  ) {
    this.isLoading.set(true);

    const reactionToSend = reactionType === currentReactionType ? null : reactionType;
    return this.http
      .put<ReactionType | null>(
        `http://localhost:8080/api/${contentType}s/reactions/${contentId}?${
          reactionToSend ? 'reactionType=' + reactionToSend : ''
        }`, {},)
      .pipe(
        finalize(() => {
          this.isLoading.set(false);
        }),
        tap(
          (data) => {
            console.log(data);
          },
          (error) => {
            this.alertService.showAlert(error, 'error');
          },
        ),
      );
  }
}
