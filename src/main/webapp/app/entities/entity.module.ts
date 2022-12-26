import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'promotion',
        loadChildren: () => import('./promotion/promotion.module').then(m => m.PromotionsPromotionModule),
      },
      {
        path: 'currency',
        loadChildren: () => import('./currency/currency.module').then(m => m.PromotionsCurrencyModule),
      },
      {
        path: 'banner',
        loadChildren: () => import('./banner/banner.module').then(m => m.PromotionsBannerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PromotionsEntityModule {}
