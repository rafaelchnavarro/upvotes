export interface IUpvotes {
  id?: number;
  message?: string;
}

export class Upvotes implements IUpvotes {
  constructor(public id?: number, public message?: string) {}
}
