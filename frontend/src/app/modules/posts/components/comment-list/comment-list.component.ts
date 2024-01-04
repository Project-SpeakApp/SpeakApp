import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentService} from "../../sevices/comment.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../../../shared/services/auth.service";
import {CommentGetModel} from "../../../../shared/types/posts/comment-get.model";
import {ReactionType} from "../../../../shared/types/posts/ReactionType.enum";
import {CommentGetListModel} from "../../../../shared/types/posts/comment-get-list.model";

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit, OnDestroy{
  @Input() postId: string = "";
  comments: CommentGetModel[] = [];

  constructor(private commentService: CommentService, private auth: AuthService) {
  }
  ngOnInit(): void {
    this.comments = this.exampleComments;
    //this.getComments();
  }
  private commentsSubscription?: Subscription;

  getComments(): void {
    this.isLoading = true;
    this.commentService.getComments(this.postId, this.auth.state().userId, this.currentPage);
    this.commentsSubscription?.unsubscribe();
    this.commentsSubscription = this.commentService.comments$.subscribe(commentList => {
      this.comments = commentList.comments;
      this.currentPage = commentList.currentPage;
      this.totalNumberOfComments = commentList.totalNumberOfComments;
    })
    this.numberOfComments = this.comments.length;
    this.isLoading = false;
  }

  isLoading: boolean = false;


  numberOfComments: number = 0;

  totalNumberOfComments: number = 0;

  currentPage: number = 0;

  ngOnDestroy(): void {
    this.commentsSubscription?.unsubscribe();
  }

  exampleComments: CommentGetModel[] = [
    {
      commentId: 'c1',
      content: 'This article is very informative and well-written.',
      createdAt: new Date('2023-01-01T09:00:00'), // Example date and time
      modifiedAt: null, // or new Date('2023-01-02T10:00:00')
      author: {
        userId: 'u1',
        fullName: 'Alice Johnson',
        profilePhotoUrl: 'https://example.com/photos/u1.jpg'
      },
      reactions: {
        sumOfReactions: 10,
        sumOfReactionsByType: new Map<ReactionType, number>() // Empty map
      }
    },
    {
      commentId: 'c2',
      content: 'Great insights on the topic!',
      createdAt: new Date('2023-01-05T15:45:00'), // Example date and time
      modifiedAt: new Date('2023-01-06T12:00:00'), // Example date and time
      author: {
        userId: 'u2',
        fullName: 'Bob Smith',
        profilePhotoUrl: 'https://example.com/photos/u2.jpg'
      },
      reactions: {
        sumOfReactions: 5,
        sumOfReactionsByType: new Map<ReactionType, number>() // Empty map
      }
    },
    {
      commentId: 'c3',
      content: 'Interesting perspective, but I have some reservations.',
      createdAt: new Date('2023-01-10T08:30:00'), // Example date and time
      modifiedAt: new Date('2023-01-11T14:20:00'), // Example date and time
      author: {
        userId: 'u3',
        fullName: 'Carol Williams',
        profilePhotoUrl: 'https://example.com/photos/u3.jpg'
      },
      reactions: {
        sumOfReactions: 8,
        sumOfReactionsByType: new Map<ReactionType, number>() // Empty map
      }
    },
    {
      commentId: 'c3',
      content: 'Interesting perspective, but I have some reservations.',
      createdAt: new Date('2023-01-10T08:30:00'), // Example date and time
      modifiedAt: new Date('2023-01-11T14:20:00'), // Example date and time
      author: {
        userId: 'u3',
        fullName: 'Carol Williams',
        profilePhotoUrl: 'https://example.com/photos/u3.jpg'
      },
      reactions: {
        sumOfReactions: 8,
        sumOfReactionsByType: new Map<ReactionType, number>() // Empty map
      }
    },
    {
      commentId: 'c3',
      content: 'Interesting perspective, but I have some reservations.',
      createdAt: new Date('2023-01-10T08:30:00'), // Example date and time
      modifiedAt: new Date('2023-01-11T14:20:00'), // Example date and time
      author: {
        userId: 'u3',
        fullName: 'Carol Williams',
        profilePhotoUrl: 'https://example.com/photos/u3.jpg'
      },
      reactions: {
        sumOfReactions: 8,
        sumOfReactionsByType: new Map<ReactionType, number>() // Empty map
      }
    },


    // More comments can be added as needed
  ];

}
