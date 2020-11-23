import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPokerProfile, PokerProfile } from 'app/shared/model/poker-profile.model';
import { PokerProfileService } from './poker-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-poker-profile-update',
  templateUrl: './poker-profile-update.component.html',
})
export class PokerProfileUpdateComponent implements OnInit {
  isSaving = false;
  regDateDp: any;
  lastDateDp: any;

  editForm = this.fb.group({
    id: [],
    ongameId: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    nickName: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    regDate: [null, [Validators.required]],
    lastDate: [null, [Validators.required]],
    photoPath: [],
    photoPathContentType: [],
    ip: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected pokerProfileService: PokerProfileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pokerProfile }) => {
      this.updateForm(pokerProfile);
    });
  }

  updateForm(pokerProfile: IPokerProfile): void {
    this.editForm.patchValue({
      id: pokerProfile.id,
      ongameId: pokerProfile.ongameId,
      nickName: pokerProfile.nickName,
      regDate: pokerProfile.regDate,
      lastDate: pokerProfile.lastDate,
      photoPath: pokerProfile.photoPath,
      photoPathContentType: pokerProfile.photoPathContentType,
      ip: pokerProfile.ip,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhipsterApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pokerProfile = this.createFromForm();
    if (pokerProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.pokerProfileService.update(pokerProfile));
    } else {
      this.subscribeToSaveResponse(this.pokerProfileService.create(pokerProfile));
    }
  }

  private createFromForm(): IPokerProfile {
    return {
      ...new PokerProfile(),
      id: this.editForm.get(['id'])!.value,
      ongameId: this.editForm.get(['ongameId'])!.value,
      nickName: this.editForm.get(['nickName'])!.value,
      regDate: this.editForm.get(['regDate'])!.value,
      lastDate: this.editForm.get(['lastDate'])!.value,
      photoPathContentType: this.editForm.get(['photoPathContentType'])!.value,
      photoPath: this.editForm.get(['photoPath'])!.value,
      ip: this.editForm.get(['ip'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPokerProfile>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
