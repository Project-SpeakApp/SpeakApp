import { Component, Input, OnChanges, OnDestroy } from '@angular/core';
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
export class UserProfileEditFormComponent implements OnDestroy, OnChanges {
  @Input() profile: ProfileGetDTO | null = {} as ProfileGetDTO;
  @Input() isLoading = false;

  constructor(private profilesService: ProfilesService, private alertService: AlertService) {}

  sub: Subscription | null = null;
  isProfileUpdateLoading = this.profilesService.profileUpdateLoading;

  firstNameControl = new FormControl('', [
    Validators.required,
    Validators.maxLength(50),
  ]);
  lastNameControl = new FormControl('', [
    Validators.required,
    Validators.maxLength(50),
  ]);
  aboutControl = new FormControl('', [
    Validators.maxLength(500),
  ]);
  dateOfBirthControl = new FormControl(new Date(), [
    Validators.required,
  ]);

  public updateProfile() {
    if (this.firstNameControl.invalid || !this.firstNameControl.value) {
      this.alertService.showAlert('First name is required and should be no more than 50 characters long.', 'error');
      return;
    }
    if (this.lastNameControl.invalid || !this.lastNameControl.value) {
      this.alertService.showAlert('Last name is required and should be no more than 50 characters long.', 'error');
      return;
    }
    if (this.aboutControl.invalid || !this.aboutControl.value) {
      this.alertService.showAlert('About should be no more than 500 characters long.', 'error');
      return;
    }
    if (this.dateOfBirthControl.invalid || !this.dateOfBirthControl.value) {
      this.alertService.showAlert('Date of birth is required.', 'error');
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

  ngOnChanges(): void {
    if (this.profile) {
      console.log(this.profile);
      this.firstNameControl.setValue(this.profile.firstName);
      this.lastNameControl.setValue(this.profile.lastName);
      this.aboutControl.setValue(this.profile.about);
      this.dateOfBirthControl.setValue(this.profile.dateOfBirth);
    }
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }
}
