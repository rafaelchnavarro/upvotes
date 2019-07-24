/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UpvotesTestModule } from '../../../test.module';
import { UpvotesDeleteDialogComponent } from 'app/entities/upvotes/upvotes-delete-dialog.component';
import { UpvotesService } from 'app/entities/upvotes/upvotes.service';

describe('Component Tests', () => {
  describe('Upvotes Management Delete Component', () => {
    let comp: UpvotesDeleteDialogComponent;
    let fixture: ComponentFixture<UpvotesDeleteDialogComponent>;
    let service: UpvotesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UpvotesTestModule],
        declarations: [UpvotesDeleteDialogComponent]
      })
        .overrideTemplate(UpvotesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UpvotesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UpvotesService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
