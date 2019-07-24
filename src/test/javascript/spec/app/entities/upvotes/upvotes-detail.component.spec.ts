/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UpvotesTestModule } from '../../../test.module';
import { UpvotesDetailComponent } from 'app/entities/upvotes/upvotes-detail.component';
import { Upvotes } from 'app/shared/model/upvotes.model';

describe('Component Tests', () => {
  describe('Upvotes Management Detail Component', () => {
    let comp: UpvotesDetailComponent;
    let fixture: ComponentFixture<UpvotesDetailComponent>;
    const route = ({ data: of({ upvotes: new Upvotes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UpvotesTestModule],
        declarations: [UpvotesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UpvotesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UpvotesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.upvotes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
