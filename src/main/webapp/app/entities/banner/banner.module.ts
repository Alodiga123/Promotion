import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromotionsSharedModule } from 'app/shared/shared.module';
import { BannerComponent } from './banner.component';
import { BannerDetailComponent } from './banner-detail.component';
import { BannerUpdateComponent } from './banner-update.component';
import { BannerDeleteDialogComponent } from './banner-delete-dialog.component';
import { bannerRoute } from './banner.route';

@NgModule({
  imports: [PromotionsSharedModule, RouterModule.forChild(bannerRoute)],
  declarations: [BannerComponent, BannerDetailComponent, BannerUpdateComponent, BannerDeleteDialogComponent],
  entryComponents: [BannerDeleteDialogComponent],
})
export class PromotionsBannerModule {}
