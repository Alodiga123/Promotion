import { PostionBanner } from 'app/shared/model/enumerations/postion-banner.model';

export interface IBanner {
  id?: number;
  name?: string;
  description?: string;
  position?: PostionBanner;
  imagenContentType?: string;
  imagen?: any;
}

export class Banner implements IBanner {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public position?: PostionBanner,
    public imagenContentType?: string,
    public imagen?: any
  ) {}
}
