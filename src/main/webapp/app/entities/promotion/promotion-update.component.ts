import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPromotion, Promotion } from 'app/shared/model/promotion.model';
import { PromotionService } from './promotion.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency/currency.service';

@Component({
  selector: 'jhi-promotion-update',
  templateUrl: './promotion-update.component.html',
})
export class PromotionUpdateComponent implements OnInit {
  isSaving = false;
  currencies: ICurrency[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    promotionType: [null, [Validators.required]],
    creationDate: [],
    responsibleDate: [],
    beginningDate: [],
    endingDate: [],
    isExclusive: [null, [Validators.required]],
    priority: [null, [Validators.required]],
    promotionalText: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    isPercent: [],
    imagen: [null, [Validators.required]],
    imagenContentType: [],
    isAmount: [],
    value: [null, [Validators.required, Validators.min(0), Validators.max(5)]],
    currency: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected promotionService: PromotionService,
    protected currencyService: CurrencyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ promotion }) => {
      if (!promotion.id) {
        const today = moment().startOf('day');
        promotion.creationDate = today;
        promotion.responsibleDate = today;
        promotion.beginningDate = today;
        promotion.endingDate = today;
      }

      this.updateForm(promotion);

      this.currencyService.query().subscribe((res: HttpResponse<ICurrency[]>) => (this.currencies = res.body || []));
    });
  }

  updateForm(promotion: IPromotion): void {
    this.editForm.patchValue({
      id: promotion.id,
      name: promotion.name,
      promotionType: promotion.promotionType,
      creationDate: promotion.creationDate ? promotion.creationDate.format(DATE_TIME_FORMAT) : null,
      responsibleDate: promotion.responsibleDate ? promotion.responsibleDate.format(DATE_TIME_FORMAT) : null,
      beginningDate: promotion.beginningDate ? promotion.beginningDate.format(DATE_TIME_FORMAT) : null,
      endingDate: promotion.endingDate ? promotion.endingDate.format(DATE_TIME_FORMAT) : null,
      isExclusive: promotion.isExclusive,
      priority: promotion.priority,
      promotionalText: promotion.promotionalText,
      enabled: promotion.enabled,
      isPercent: promotion.isPercent,
      imagen: promotion.imagen,
      imagenContentType: promotion.imagenContentType,
      isAmount: promotion.isAmount,
      value: promotion.value,
      currency: promotion.currency,
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
    const promotion = this.createFromForm();
    if (promotion.id !== undefined) {
      this.subscribeToSaveResponse(this.promotionService.update(promotion));
    } else {
      this.subscribeToSaveResponse(this.promotionService.create(promotion));
    }
  }

  private createFromForm(): IPromotion {
    return {
      ...new Promotion(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      promotionType: this.editForm.get(['promotionType'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      responsibleDate: this.editForm.get(['responsibleDate'])!.value
        ? moment(this.editForm.get(['responsibleDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      beginningDate: this.editForm.get(['beginningDate'])!.value
        ? moment(this.editForm.get(['beginningDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      endingDate: this.editForm.get(['endingDate'])!.value ? moment(this.editForm.get(['endingDate'])!.value, DATE_TIME_FORMAT) : undefined,
      isExclusive: this.editForm.get(['isExclusive'])!.value,
      priority: this.editForm.get(['priority'])!.value,
      promotionalText: this.editForm.get(['promotionalText'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      isPercent: this.editForm.get(['isPercent'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      isAmount: this.editForm.get(['isAmount'])!.value,
      value: this.editForm.get(['value'])!.value,
      currency: this.editForm.get(['currency'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPromotion>>): void {
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

  trackById(index: number, item: ICurrency): any {
    return item.id;
  }
}
