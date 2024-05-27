import {Component, OnDestroy, OnInit} from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';
import { ActivatedRoute } from '@angular/router';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import {Observable, Subscription} from 'rxjs';

@Component({
  selector: 'app-user-profile-info-page',
  templateUrl: './user-profile-info-page.component.html'
})
export class UserProfileInfoPageComponent implements OnInit, OnDestroy {

  constructor(private profilesService: ProfilesService, private route: ActivatedRoute) { }

  profile$: Observable<ProfileGetDTO> | null = null ;
  isLoading = this.profilesService.isLoading;
  subscription = new Subscription();

  loadProfile(r: any): void {
    const userId= this.route.pathFromRoot[1].snapshot.paramMap.get('id');
    if (!userId) {
      console.log('No user id');
      return;
    }
    this.profile$ = this.profilesService.getProfile(userId);
  }

  ngOnInit(): void {
    this.subscription.add(this.route.pathFromRoot[1].params.subscribe((r) => {
      this.loadProfile(r);
    }));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
