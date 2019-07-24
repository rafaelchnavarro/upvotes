import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUpvotes } from 'app/shared/model/upvotes.model';
import { UpvotesService } from './upvotes.service';

@Component({
  selector: 'jhi-upvotes-delete-dialog',
  templateUrl: './upvotes-delete-dialog.component.html'
})
export class UpvotesDeleteDialogComponent {
  upvotes: IUpvotes;

  constructor(protected upvotesService: UpvotesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.upvotesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'upvotesListModification',
        content: 'Deleted an upvotes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-upvotes-delete-popup',
  template: ''
})
export class UpvotesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ upvotes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UpvotesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.upvotes = upvotes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/upvotes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/upvotes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
