import { Injectable } from '@angular/core';
import { Subscription } from 'rxjs';
//import { AngularFireDatabase, FirebaseObjectObservable } from 'angularfire2/database';

@Injectable()
export class UpvoteService {
  constructor() {}

  getItemVotes(itemId): any {
    return new Subscription();
  }

  updateUserVote(itemId, userId, vote): void {}
}
