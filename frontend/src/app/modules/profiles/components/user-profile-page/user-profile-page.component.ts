import {Component, OnDestroy, OnInit} from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';
import {Observable, Subscription} from 'rxjs';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-user-profile-page',
  templateUrl: './user-profile-page.component.html'
})
export class UserProfilePageComponent implements OnInit, OnDestroy {

  constructor(private profilesService: ProfilesService, private route: ActivatedRoute) { }

  profile$: Observable<ProfileGetDTO> | null = null;
  isLoading = this.profilesService.isLoading;

  routeSub = new Subscription();

  loadProfile(r: Params): void {
    const userId = this.route.snapshot.paramMap.get('id')
    if (!userId) {
      console.log('No user id');
      return;
    }
    this.profile$ = this.profilesService.getProfile(userId);
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe((r) => {
      this.loadProfile(r);
    });
  }

  ngOnDestroy(): void {
    this.routeSub.unsubscribe();
  }
}
