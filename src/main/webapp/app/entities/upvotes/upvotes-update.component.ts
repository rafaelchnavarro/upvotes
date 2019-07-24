import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUpvotes, Upvotes } from 'app/shared/model/upvotes.model';
import { UpvotesService } from './upvotes.service';

@Component({
  selector: 'jhi-upvotes-update',
  templateUrl: './upvotes-update.component.html'
})
export class UpvotesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    message: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]]
  });

  constructor(protected upvotesService: UpvotesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ upvotes }) => {
      this.updateForm(upvotes);
    });
  }

  updateForm(upvotes: IUpvotes) {
    this.editForm.patchValue({
      id: upvotes.id,
      message: upvotes.message
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const upvotes = this.createFromForm();
    if (upvotes.id !== undefined) {
      this.subscribeToSaveResponse(this.upvotesService.update(upvotes));
    } else {
      this.subscribeToSaveResponse(this.upvotesService.create(upvotes));
    }
  }

  private createFromForm(): IUpvotes {
    return {
      ...new Upvotes(),
      id: this.editForm.get(['id']).value,
      message: this.editForm.get(['message']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUpvotes>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
