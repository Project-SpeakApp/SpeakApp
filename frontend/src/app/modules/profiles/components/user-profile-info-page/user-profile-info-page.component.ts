import { Component, OnInit } from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';
import { ActivatedRoute } from '@angular/router';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-profile-info-page',
  templateUrl: './user-profile-info-page.component.html'
})
export class UserProfileInfoPageComponent implements OnInit {

  constructor(private profilesService: ProfilesService, private route: ActivatedRoute) { }

  profile$: Observable<ProfileGetDTO> | null = null ;

  ngOnInit(): void {
    const userId = this.route.pathFromRoot[1].snapshot.paramMap.get('id');
    if (!userId) {
      console.log('No user id');
      return;
    }
    this.profile$ = this.profilesService.getProfile(userId);
  }
}
