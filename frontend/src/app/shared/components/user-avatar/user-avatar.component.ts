import {Component, Input, OnChanges, OnDestroy, OnInit, signal, SimpleChanges} from '@angular/core';
import {ImageService} from "../../services/image.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-user-avatar',
  templateUrl: './user-avatar.component.html'
})
export class UserAvatarComponent implements OnInit, OnDestroy, OnChanges {
  @Input() src: string | null = null;
  @Input() size: 'sm' | 'm' | 'xl' | 'xxl' = 'm';

  imageLoading = signal(false);
  imageSubscription = new Subscription();
  avatarSrc = '';

  constructor(private imageService: ImageService) { }

  ngOnInit(): void {
    if (this.src) {
      this.imageLoading.set(true);
      this.imageSubscription.add(this.imageService.downloadImage(this.src).subscribe((blob) => {
        this.avatarSrc = URL.createObjectURL(blob);
        this.imageLoading.set(false);
      }));
    }
    else {
      this.avatarSrc = 'https://th.bing.com/th/id/OIP.eyhIau9Wqaz8_VhUIomLWgAAAA?rs=1&pid=ImgDetMain';
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['src']) {
      this.ngOnInit();
    }
  }

  ngOnDestroy(): void {
    this.imageSubscription.unsubscribe();
  }
}
