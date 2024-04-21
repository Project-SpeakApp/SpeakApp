import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges, signal} from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import {AuthService} from "../../../../shared/services/auth.service";
import TypeMedia from "../../../../shared/types/media/type-media";
import ImageSnippet from "../../../../shared/types/media/ImageSnippet";
import {Subscription} from "rxjs";
import {ImageService} from "../../../../shared/services/image.service";
import {ProfilesService} from "../../services/profiles.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit, OnDestroy, OnChanges {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;

  authState = this.authService.state;

  imageLoading = signal(false);
  imageSubstription = new Subscription();

  avatarSrc = '';

  constructor(private authService: AuthService, private imageService: ImageService, private profileService: ProfilesService) {
  }

  processImage(imageInput: any) {
    this.imageLoading.set(true);
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.imageSubstription.add(this.imageService.uploadImage(file, TypeMedia.AVATAR).subscribe((res) => {
        this.imageSubstription.add(this.profileService.updateProfilePhoto(res).subscribe((_) => {
          if (this.profile) this.profile.profilePhotoId = res;
          this.ngOnInit();
        }));
      }));
    })

    reader.readAsDataURL(file);
  }
  changePassword() {
    this.authService.manageAccount();
  }

  ngOnInit(): void {
    console.log(this.profile)
    if (this.profile?.profilePhotoId) {
      this.imageLoading.set(true);
      this.imageSubstription.add(this.imageService.downloadImage(this.profile.profilePhotoId).subscribe((blob) => {
        this.avatarSrc = URL.createObjectURL(blob);
        this.imageLoading.set(false);
      }));
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['profile']) {
      this.ngOnInit()
    }
  }

  ngOnDestroy(): void {
    this.imageSubstription.unsubscribe();
  }
}
