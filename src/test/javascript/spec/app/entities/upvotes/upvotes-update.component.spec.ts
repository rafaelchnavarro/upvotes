/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UpvotesTestModule } from '../../../test.module';
import { UpvotesUpdateComponent } from 'app/entities/upvotes/upvotes-update.component';
import { UpvotesService } from 'app/entities/upvotes/upvotes.service';
import { Upvotes } from 'app/shared/model/upvotes.model';

describe('Component Tests', () => {
  describe('Upvotes Management Update Component', () => {
    let comp: UpvotesUpdateComponent;
    let fixture: ComponentFixture<UpvotesUpdateComponent>;
    let service: UpvotesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UpvotesTestModule],
        declarations: [UpvotesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UpvotesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UpvotesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UpvotesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Upvotes(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Upvotes();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
