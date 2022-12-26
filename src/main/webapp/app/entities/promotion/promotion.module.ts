import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromotionsSharedModule } from 'app/shared/shared.module';
import { PromotionComponent } from './promotion.component';
import { PromotionDetailComponent } from './promotion-detail.component';
import { PromotionUpdateComponent } from './promotion-update.component';
import { PromotionDeleteDialogComponent } from './promotion-delete-dialog.component';
import { promotionRoute } from './promotion.route';

@NgModule({
  imports: [PromotionsSharedModule, RouterModule.forChild(promotionRoute)],
  declarations: [PromotionComponent, PromotionDetailComponent, PromotionUpdateComponent, PromotionDeleteDialogComponent],
  entryComponents: [PromotionDeleteDialogComponent],
})
export class PromotionsPromotionModule {}
