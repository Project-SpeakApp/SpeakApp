import {Component, Input, OnChanges, OnDestroy, OnInit, signal, SimpleChanges} from '@angular/core';
import ProfileGetDTO, {FriendStatus} from '../../types/ProfileGetDTO';
import {AuthService} from "../../../../shared/services/auth.service";
import TypeMedia from "../../../../shared/types/media/type-media";
import {Subscription, tap} from "rxjs";
import {ImageService} from "../../../../shared/services/image.service";
import {ProfilesService} from "../../services/profiles.service";
import {AlertService} from "../../../../shared/services/alert.service";
import {FriendsService} from "../../services/friends.service";

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

  unfriendLoading = false;
  acceptRequestLoading = false;
  rejectRequestLoading = false;
  sendRequestLoading = false;

  constructor(
    private authService: AuthService,
    private imageService: ImageService,
    private profileService: ProfilesService,
    private friendsService: FriendsService,
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

  unfriend() {
    if (!this.profile?.userId) return;

    this.unfriendLoading = true;
    this.imageSubscription.add(this.friendsService.unfriend(this.profile.userId).subscribe((_) => {
      this.profile!.friendStatus = null;
      this.unfriendLoading = false;
      this.alertService.showAlert("Unfriended", "success");
    }));
  }

  acceptRequest() {
    if (!this.profile?.userId) return;

    this.acceptRequestLoading = true;
    this.imageSubscription.add(this.friendsService.getFriendRequests(0, 99999).subscribe((res) => {
      const request = res.friendRequests.find((request) => request.requester.userId === this.profile!.userId);
      if (!request) return;

      this.imageSubscription.add(this.friendsService.acceptFriendRequest(request.id).subscribe((_) => {
        this.profile!.friendStatus = FriendStatus.FRIEND;
        this.acceptRequestLoading = false;
        this.alertService.showAlert("Friend request accepted", "success");
      }));
    }));
  }

  rejectRequest() {
    if (!this.profile?.userId) return;

    this.rejectRequestLoading = true;
    this.imageSubscription.add(this.friendsService.getFriendRequests(0, 99999).subscribe((res) => {
      const request = res.friendRequests.find((request) => request.requester.userId === this.profile!.userId);
      if (!request) return;

      this.imageSubscription.add(this.friendsService.rejectFriendRequest(request.id).subscribe((_) => {
        this.profile!.friendStatus = null;
        this.rejectRequestLoading = false;
        this.alertService.showAlert("Friend request rejected", "success");
      }));
    }));
  }

  sendRequest() {
    if (!this.profile?.userId) return;

    this.sendRequestLoading = true;
    this.imageSubscription.add(this.friendsService.sendFriendRequest(this.profile.userId).subscribe((_) => {
      this.profile!.friendStatus = FriendStatus.REQUEST_SENT;
      this.sendRequestLoading = false;
      this.alertService.showAlert("Friend request sent", "success");
    }));
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

  protected readonly FriendStatus = FriendStatus;
}
