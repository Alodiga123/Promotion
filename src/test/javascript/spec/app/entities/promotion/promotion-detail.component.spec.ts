import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PromotionsTestModule } from '../../../test.module';
import { PromotionDetailComponent } from 'app/entities/promotion/promotion-detail.component';
import { Promotion } from 'app/shared/model/promotion.model';

describe('Component Tests', () => {
  describe('Promotion Management Detail Component', () => {
    let comp: PromotionDetailComponent;
    let fixture: ComponentFixture<PromotionDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ promotion: new Promotion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PromotionsTestModule],
        declarations: [PromotionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PromotionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PromotionDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load promotion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.promotion).toEqual(jasmine.objectContaining({ id: 123 }));
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
