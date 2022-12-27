import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PromotionsTestModule } from '../../../test.module';
import { BannerDetailComponent } from 'app/entities/banner/banner-detail.component';
import { Banner } from 'app/shared/model/banner.model';

describe('Component Tests', () => {
  describe('Banner Management Detail Component', () => {
    let comp: BannerDetailComponent;
    let fixture: ComponentFixture<BannerDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ banner: new Banner(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PromotionsTestModule],
        declarations: [BannerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BannerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BannerDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load banner on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.banner).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
