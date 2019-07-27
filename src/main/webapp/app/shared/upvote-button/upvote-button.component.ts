import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { UpvoteService } from './upvote.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IUpvotes, Upvotes } from 'app/shared/model/upvotes.model';
import { UpvotesService } from 'app/entities/upvotes';

@Component({
  selector: 'upvote-button',
  templateUrl: './upvote-button.component.html',
  styleUrls: ['./upvote-button.component.scss']
})
export class UpvoteButtonComponent implements OnInit, OnDestroy {
  @Input() messageId;
  @Input() votes;

  voteCount: number = 0;
  userVote: number = 0;

  //subscription;

  constructor(private upvoteService: UpvoteService, protected upvotesService: UpvotesService) {}

  ngOnInit() {
    this.voteCount = this.votes;
  }

  upvote() {
    let vote = this.userVote == 1 ? 0 : 1;
    this.voteCount = this.voteCount + vote;
    debugger;
    this.subscribeToSaveResponse(this.upvotesService.vote(this.messageId, this.voteCount));

    //this.upvoteService.updateUserVote(this.votes, this.messageId, this.voteCount);
  }

  downvote() {
    let vote = this.userVote == -1 ? 0 : -1;
    this.voteCount = this.voteCount + vote;

    this.subscribeToSaveResponse(this.upvotesService.vote(this.messageId, this.voteCount));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUpvotes>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {}

  protected onSaveError() {}

  ngOnDestroy() {
    //  this.subscription.unsubscribe();
  }
}
