import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IBanner, Banner } from 'app/shared/model/banner.model';
import { BannerService } from './banner.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-banner-update',
  templateUrl: './banner-update.component.html',
})
export class BannerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    position: [null, [Validators.required]],
    imagen: [null, [Validators.required]],
    imagenContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected bannerService: BannerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banner }) => {
      this.updateForm(banner);
    });
  }

  updateForm(banner: IBanner): void {
    this.editForm.patchValue({
      id: banner.id,
      name: banner.name,
      description: banner.description,
      position: banner.position,
      imagen: banner.imagen,
      imagenContentType: banner.imagenContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('promotionsApp.error', { ...err, key: 'error.file.' + err.key })
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
    const banner = this.createFromForm();
    if (banner.id !== undefined) {
      this.subscribeToSaveResponse(this.bannerService.update(banner));
    } else {
      this.subscribeToSaveResponse(this.bannerService.create(banner));
    }
  }

  private createFromForm(): IBanner {
    return {
      ...new Banner(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      position: this.editForm.get(['position'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanner>>): void {
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
