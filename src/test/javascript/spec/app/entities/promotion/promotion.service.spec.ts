import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PromotionService } from 'app/entities/promotion/promotion.service';
import { IPromotion, Promotion } from 'app/shared/model/promotion.model';
import { PromotionType } from 'app/shared/model/enumerations/promotion-type.model';

describe('Service Tests', () => {
  describe('Promotion Service', () => {
    let injector: TestBed;
    let service: PromotionService;
    let httpMock: HttpTestingController;
    let elemDefault: IPromotion;
    let expectedResult: IPromotion | IPromotion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PromotionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Promotion(
        0,
        'AAAAAAA',
        PromotionType.REFERRED,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        false,
        0,
        'AAAAAAA',
        false,
        false,
        'image/png',
        'AAAAAAA',
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            responsibleDate: currentDate.format(DATE_TIME_FORMAT),
            beginningDate: currentDate.format(DATE_TIME_FORMAT),
            endingDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Promotion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            responsibleDate: currentDate.format(DATE_TIME_FORMAT),
            beginningDate: currentDate.format(DATE_TIME_FORMAT),
            endingDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            responsibleDate: currentDate,
            beginningDate: currentDate,
            endingDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Promotion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Promotion', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            promotionType: 'BBBBBB',
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            responsibleDate: currentDate.format(DATE_TIME_FORMAT),
            beginningDate: currentDate.format(DATE_TIME_FORMAT),
            endingDate: currentDate.format(DATE_TIME_FORMAT),
            isExclusive: true,
            priority: 1,
            promotionalText: 'BBBBBB',
            enabled: true,
            isPercent: true,
            imagen: 'BBBBBB',
            isAmount: true,
            value: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            responsibleDate: currentDate,
            beginningDate: currentDate,
            endingDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Promotion', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            promotionType: 'BBBBBB',
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            responsibleDate: currentDate.format(DATE_TIME_FORMAT),
            beginningDate: currentDate.format(DATE_TIME_FORMAT),
            endingDate: currentDate.format(DATE_TIME_FORMAT),
            isExclusive: true,
            priority: 1,
            promotionalText: 'BBBBBB',
            enabled: true,
            isPercent: true,
            imagen: 'BBBBBB',
            isAmount: true,
            value: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            responsibleDate: currentDate,
            beginningDate: currentDate,
            endingDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Promotion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
