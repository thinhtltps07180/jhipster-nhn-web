import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PokerProfileService } from 'app/entities/poker-profile/poker-profile.service';
import { IPokerProfile, PokerProfile } from 'app/shared/model/poker-profile.model';

describe('Service Tests', () => {
  describe('PokerProfile Service', () => {
    let injector: TestBed;
    let service: PokerProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IPokerProfile;
    let expectedResult: IPokerProfile | IPokerProfile[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PokerProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PokerProfile(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'image/png', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            regDate: currentDate.format(DATE_FORMAT),
            lastDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PokerProfile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            regDate: currentDate.format(DATE_FORMAT),
            lastDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            regDate: currentDate,
            lastDate: currentDate,
          },
          returnedFromService
        );

        service.create(new PokerProfile()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PokerProfile', () => {
        const returnedFromService = Object.assign(
          {
            ongameId: 'BBBBBB',
            nickName: 'BBBBBB',
            regDate: currentDate.format(DATE_FORMAT),
            lastDate: currentDate.format(DATE_FORMAT),
            photoPath: 'BBBBBB',
            ip: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            regDate: currentDate,
            lastDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PokerProfile', () => {
        const returnedFromService = Object.assign(
          {
            ongameId: 'BBBBBB',
            nickName: 'BBBBBB',
            regDate: currentDate.format(DATE_FORMAT),
            lastDate: currentDate.format(DATE_FORMAT),
            photoPath: 'BBBBBB',
            ip: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            regDate: currentDate,
            lastDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PokerProfile', () => {
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
