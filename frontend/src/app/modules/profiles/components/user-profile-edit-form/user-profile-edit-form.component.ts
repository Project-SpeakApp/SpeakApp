import { Component, Input, OnDestroy } from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import { ProfilesService } from '../../services/profiles.service';
import { FormControl, Validators } from '@angular/forms';
import ProfileUpdateDTO from '../../types/ProfileUpdateDTO';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-user-profile-edit-form',
  templateUrl: './user-profile-edit-form.component.html',
})
export class UserProfileEditFormComponent implements OnDestroy {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;

  constructor(private profilesService: ProfilesService, private alertService: AlertService) {}

  sub: Subscription | null = null;
  isProfileUpdateLoading = this.profilesService.profileUpdateLoading;

  firstNameControl = new FormControl(this.profile?.firstName, [
    Validators.required,
    Validators.maxLength(50),
  ]);
  lastNameControl = new FormControl(this.profile?.lastName, [
    Validators.required,
    Validators.maxLength(50),
  ]);
  aboutControl = new FormControl(this.profile?.about, [
    Validators.maxLength(500),
  ]);
  dateOfBirthControl = new FormControl(this.profile?.dateOfBirth, [
    Validators.required,
  ]);

  public updateProfile() {
    if (
      this.firstNameControl.invalid ||
      this.lastNameControl.invalid ||
      this.aboutControl.invalid ||
      this.dateOfBirthControl.invalid ||
      !this.firstNameControl.value ||
      !this.lastNameControl.value ||
      !this.dateOfBirthControl.value
    ) {
      return;
    }
    const profileUpdateDTO: ProfileUpdateDTO = {
      firstName: this.firstNameControl.value,
      lastName: this.lastNameControl.value,
      about: this.aboutControl.value,
      dateOfBirth: this.dateOfBirthControl.value,
    };
    this.sub = this.profilesService.updateProfile(profileUpdateDTO).subscribe();
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }
}
