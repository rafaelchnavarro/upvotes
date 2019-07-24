import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UpvotesSharedModule } from 'app/shared';
import {
  UpvotesComponent,
  UpvotesDetailComponent,
  UpvotesUpdateComponent,
  UpvotesDeletePopupComponent,
  UpvotesDeleteDialogComponent,
  upvotesRoute,
  upvotesPopupRoute
} from './';

const ENTITY_STATES = [...upvotesRoute, ...upvotesPopupRoute];

@NgModule({
  imports: [UpvotesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UpvotesComponent,
    UpvotesDetailComponent,
    UpvotesUpdateComponent,
    UpvotesDeleteDialogComponent,
    UpvotesDeletePopupComponent
  ],
  entryComponents: [UpvotesComponent, UpvotesUpdateComponent, UpvotesDeleteDialogComponent, UpvotesDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UpvotesUpvotesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
