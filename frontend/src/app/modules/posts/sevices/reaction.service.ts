import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { finalize } from 'rxjs';
import { ReactionType } from 'src/app/shared/types/posts/ReactionType.enum';

@Injectable({
  providedIn: 'root',
})
export class ReactionService {
  constructor(
    private http: HttpClient,
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
      );
  }
}
