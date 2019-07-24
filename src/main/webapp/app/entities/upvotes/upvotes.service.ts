import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUpvotes } from 'app/shared/model/upvotes.model';

type EntityResponseType = HttpResponse<IUpvotes>;
type EntityArrayResponseType = HttpResponse<IUpvotes[]>;

@Injectable({ providedIn: 'root' })
export class UpvotesService {
  public resourceUrl = SERVER_API_URL + 'api/upvotes';

  constructor(protected http: HttpClient) {}

  create(upvotes: IUpvotes): Observable<EntityResponseType> {
    return this.http.post<IUpvotes>(this.resourceUrl, upvotes, { observe: 'response' });
  }

  update(upvotes: IUpvotes): Observable<EntityResponseType> {
    return this.http.put<IUpvotes>(this.resourceUrl, upvotes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUpvotes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUpvotes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
