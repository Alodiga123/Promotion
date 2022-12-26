import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPromotion } from 'app/shared/model/promotion.model';

type EntityResponseType = HttpResponse<IPromotion>;
type EntityArrayResponseType = HttpResponse<IPromotion[]>;

@Injectable({ providedIn: 'root' })
export class PromotionService {
  public resourceUrl = SERVER_API_URL + 'api/promotions';

  constructor(protected http: HttpClient) {}

  create(promotion: IPromotion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(promotion);
    return this.http
      .post<IPromotion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(promotion: IPromotion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(promotion);
    return this.http
      .put<IPromotion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPromotion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPromotion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(promotion: IPromotion): IPromotion {
    const copy: IPromotion = Object.assign({}, promotion, {
      creationDate: promotion.creationDate && promotion.creationDate.isValid() ? promotion.creationDate.toJSON() : undefined,
      responsibleDate: promotion.responsibleDate && promotion.responsibleDate.isValid() ? promotion.responsibleDate.toJSON() : undefined,
      beginningDate: promotion.beginningDate && promotion.beginningDate.isValid() ? promotion.beginningDate.toJSON() : undefined,
      endingDate: promotion.endingDate && promotion.endingDate.isValid() ? promotion.endingDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.responsibleDate = res.body.responsibleDate ? moment(res.body.responsibleDate) : undefined;
      res.body.beginningDate = res.body.beginningDate ? moment(res.body.beginningDate) : undefined;
      res.body.endingDate = res.body.endingDate ? moment(res.body.endingDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((promotion: IPromotion) => {
        promotion.creationDate = promotion.creationDate ? moment(promotion.creationDate) : undefined;
        promotion.responsibleDate = promotion.responsibleDate ? moment(promotion.responsibleDate) : undefined;
        promotion.beginningDate = promotion.beginningDate ? moment(promotion.beginningDate) : undefined;
        promotion.endingDate = promotion.endingDate ? moment(promotion.endingDate) : undefined;
      });
    }
    return res;
  }
}
