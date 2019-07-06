import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UpvotesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [UpvotesSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [UpvotesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UpvotesSharedModule {
  static forRoot() {
    return {
      ngModule: UpvotesSharedModule
    };
  }
}
