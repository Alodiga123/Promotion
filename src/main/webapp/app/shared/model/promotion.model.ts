import { Moment } from 'moment';
import { ICurrency } from 'app/shared/model/currency.model';
import { PromotionType } from 'app/shared/model/enumerations/promotion-type.model';
import { TipoTransaction } from 'app/shared/model/enumerations/tipo-transaction.model';

export interface IPromotion {
  id?: number;
  name?: string;
  promotionType?: PromotionType;
  creationDate?: Moment;
  responsibleDate?: Moment;
  beginningDate?: Moment;
  endingDate?: Moment;
  isExclusive?: boolean;
  priority?: number;
  promotionalText?: string;
  enabled?: boolean;
  isPercent?: boolean;
  imagenContentType?: string;
  imagen?: any;
  isAmount?: boolean;
  value?: number;
  amount?: number;
  transactionType?: TipoTransaction;
  currency?: ICurrency;
}

export class Promotion implements IPromotion {
  constructor(
    public id?: number,
    public name?: string,
    public promotionType?: PromotionType,
    public creationDate?: Moment,
    public responsibleDate?: Moment,
    public beginningDate?: Moment,
    public endingDate?: Moment,
    public isExclusive?: boolean,
    public priority?: number,
    public promotionalText?: string,
    public enabled?: boolean,
    public isPercent?: boolean,
    public imagenContentType?: string,
    public imagen?: any,
    public isAmount?: boolean,
    public value?: number,
    public amount?: number,
    public transactionType?: TipoTransaction,
    public currency?: ICurrency
  ) {
    this.isExclusive = this.isExclusive || false;
    this.enabled = this.enabled || false;
    this.isPercent = this.isPercent || false;
    this.isAmount = this.isAmount || false;
  }
}
