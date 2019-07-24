import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUpvotes } from 'app/shared/model/upvotes.model';

@Component({
  selector: 'jhi-upvotes-detail',
  templateUrl: './upvotes-detail.component.html'
})
export class UpvotesDetailComponent implements OnInit {
  upvotes: IUpvotes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ upvotes }) => {
      this.upvotes = upvotes;
    });
  }

  previousState() {
    window.history.back();
  }
}
