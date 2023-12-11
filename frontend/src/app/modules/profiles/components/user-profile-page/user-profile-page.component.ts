import { Component, OnInit } from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';
import { Observable } from 'rxjs';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-profile-page',
  templateUrl: './user-profile-page.component.html'
})
export class UserProfilePageComponent implements OnInit {

  constructor(private profilesService: ProfilesService, private route: ActivatedRoute) { }

  profile$: Observable<ProfileGetDTO> | null = null;

  ngOnInit(): void {
    const userId = this.route.snapshot.paramMap.get('id');
    if (!userId) {
      console.log('No book id');
      return;
    }
    this.profile$ = this.profilesService.getProfile(userId);
  }
}
