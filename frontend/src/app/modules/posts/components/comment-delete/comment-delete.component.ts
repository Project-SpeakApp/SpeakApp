import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {AlertService} from "../../../../shared/services/alert.service";
import {AuthService} from "../../../../shared/services/auth.service";

@Component({
  selector: 'app-comment-delete',
  templateUrl: './comment-delete.component.html',
  styleUrls: ['./comment-delete.component.css']
})
export class CommentDeleteComponent implements OnDestroy, OnInit{

  @Input() authorId: string = '';

  @Input() commentId: string = '';
  visible: boolean = false;
  isLoading: boolean = false;

  constructor(private alertService: AlertService, private authService: AuthService) {
  }
  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    if(this.authService.state().userId === this.authorId) {
      this.visible = true;
    }
  }

}
