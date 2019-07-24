import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Upvotes } from 'app/shared/model/upvotes.model';
import { UpvotesService } from './upvotes.service';
import { UpvotesComponent } from './upvotes.component';
import { UpvotesDetailComponent } from './upvotes-detail.component';
import { UpvotesUpdateComponent } from './upvotes-update.component';
import { UpvotesDeletePopupComponent } from './upvotes-delete-dialog.component';
import { IUpvotes } from 'app/shared/model/upvotes.model';

@Injectable({ providedIn: 'root' })
export class UpvotesResolve implements Resolve<IUpvotes> {
  constructor(private service: UpvotesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUpvotes> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Upvotes>) => response.ok),
        map((upvotes: HttpResponse<Upvotes>) => upvotes.body)
      );
    }
    return of(new Upvotes());
  }
}

export const upvotesRoute: Routes = [
  {
    path: '',
    component: UpvotesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'upvotesApp.upvotes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UpvotesDetailComponent,
    resolve: {
      upvotes: UpvotesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'upvotesApp.upvotes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UpvotesUpdateComponent,
    resolve: {
      upvotes: UpvotesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'upvotesApp.upvotes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UpvotesUpdateComponent,
    resolve: {
      upvotes: UpvotesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'upvotesApp.upvotes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const upvotesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UpvotesDeletePopupComponent,
    resolve: {
      upvotes: UpvotesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'upvotesApp.upvotes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
