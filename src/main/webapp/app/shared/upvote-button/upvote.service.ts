import { Injectable } from '@angular/core';
//import { AngularFireDatabase, FirebaseObjectObservable } from 'angularfire2/database';

@Injectable()
export class UpvoteService {
  constructor(private db: any) {}

  getItemVotes(itemId): any {
    // Gets total votes
    return this.db.object(`upvotes/${itemId}`);
  }

  updateUserVote(itemId, userId, vote): void {
    // Creates or updates user's vote
    let data = {};
    data[userId] = vote;
    this.db.object(`upvotes/${itemId}`).update(data);
  }
}
