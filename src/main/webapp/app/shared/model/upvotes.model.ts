export interface IUpvotes {
  id?: number;
  message?: string;
  vote?: number;
}

export class Upvotes implements IUpvotes {
  constructor(public id?: number, public message?: string, public vote?: number) {}
}
