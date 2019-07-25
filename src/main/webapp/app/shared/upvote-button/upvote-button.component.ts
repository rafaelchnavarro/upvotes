import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { UpvoteService } from './upvote.service';
import { sum, values } from 'lodash';
@Component({
  selector: 'upvote-button',
  templateUrl: './upvote-button.component.html',
  styleUrls: ['./upvote-button.component.scss']
})
export class UpvoteButtonComponent implements OnInit, OnDestroy {
  @Input() userId;
  @Input() votes;

  voteCount: number = 0;
  userVote: number = 0;

  //subscription;

  constructor(private upvoteService: UpvoteService) {}

  ngOnInit() {
    this.voteCount = this.votes;
  }

  upvote() {
    let vote = this.userVote == 1 ? 0 : 1;
    this.voteCount = this.voteCount + vote;
    console.log(vote);
    this.upvoteService.updateUserVote(this.votes, this.userId, vote);
  }

  downvote() {
    let vote = this.userVote == -1 ? 0 : -1;
    this.voteCount = this.voteCount + vote;
    console.log(vote);
    this.upvoteService.updateUserVote(this.votes, this.userId, vote);
  }

  ngOnDestroy() {
    //  this.subscription.unsubscribe();
  }
}
