import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges, signal} from '@angular/core';
import ProfileGetDTO from '../../types/ProfileGetDTO';
import {AuthService} from "../../../../shared/services/auth.service";
import TypeMedia from "../../../../shared/types/media/type-media";
import {Subscription, tap} from "rxjs";
import {ImageService} from "../../../../shared/services/image.service";
import {ProfilesService} from "../../services/profiles.service";
import {AlertService} from "../../../../shared/services/alert.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit, OnDestroy, OnChanges {
  @Input() profile: ProfileGetDTO | null = null;
  @Input() isLoading = false;

  authState = this.authService.state;

  imageSubscription = new Subscription();

  avatarLoading = signal(false);
  backgroundLoading = signal(false);
  avatarSrc = '';
  backgroundSrc = '';

  constructor(
    private authService: AuthService,
    private imageService: ImageService,
    private profileService: ProfilesService,
    private alertService: AlertService) {
  }

  processImage(imageInput: any, type: 'avatar' | 'background' = 'avatar') {
    if (this.authState().userId !== this.profile?.userId) return;

    if (type === 'avatar') {
      this.avatarLoading.set(true);
    } else {
      this.backgroundLoading.set(true);
    }

    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.imageSubscription.add(this.imageService.uploadImage(file, TypeMedia.AVATAR).pipe(
        tap((res) => {
          if (type === 'avatar')
            this.updateAvatar(res);
          else
            this.updateBackground(res);
        }, (error) => {
          if (type === 'avatar')
            this.avatarLoading.set(false);
          else
            this.backgroundLoading.set(false);
        })).subscribe());
    })

    reader.readAsDataURL(file);
  }

  updateAvatar(res: string) {
    this.imageSubscription.add(this.profileService.updateProfilePhoto(res).subscribe((_) => {
      if (this.profile){
        this.profile.profilePhotoId = res;
        this.authService.updateProfilePhoto(res);
        this.alertService.showAlert('Profile photo updated', 'success');
        this.loadProfilePhoto();
      }
    }));
  }

  updateBackground(res: string) {
    this.imageSubscription.add(this.profileService.updateBackgroundPhoto(res).subscribe((_) => {
      if (this.profile){
        this.profile.bgPhotoId = res;
        this.alertService.showAlert('Background photo updated', 'success');
        this.loadBackgroundPhoto();
      }
    }));
  }

  changePassword() {
    this.authService.manageAccount();
  }

  loadProfilePhoto() {
    if (this.profile?.profilePhotoId) {
      this.avatarLoading.set(true);
      this.imageSubscription.add(this.imageService.downloadImage(this.profile.profilePhotoId).subscribe((blob) => {
        this.avatarSrc = URL.createObjectURL(blob);
        this.avatarLoading.set(false);
      }));
    }
    else {
      this.avatarSrc = 'https://th.bing.com/th/id/OIP.eyhIau9Wqaz8_VhUIomLWgAAAA?rs=1&pid=ImgDetMain';
    }
  }

  loadBackgroundPhoto() {
    if (this.profile?.bgPhotoId) {
      this.backgroundLoading.set(true);
      this.imageSubscription.add(this.imageService.downloadImage(this.profile.bgPhotoId).subscribe((blob) => {
        this.backgroundSrc = URL.createObjectURL(blob);
        this.backgroundLoading.set(false);
      }));
    }
    else {
      this.backgroundSrc = 'https://wallpaperaccess.com/full/1419752.jpg';
    }
  }

  ngOnInit(): void {
    this.loadProfilePhoto();
    this.loadBackgroundPhoto();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['profile']) {
      this.ngOnInit()
    }
  }

  ngOnDestroy(): void {
    this.imageSubscription.unsubscribe();
  }
}
