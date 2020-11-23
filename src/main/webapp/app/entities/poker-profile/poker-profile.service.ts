import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPokerProfile } from 'app/shared/model/poker-profile.model';

type EntityResponseType = HttpResponse<IPokerProfile>;
type EntityArrayResponseType = HttpResponse<IPokerProfile[]>;

@Injectable({ providedIn: 'root' })
export class PokerProfileService {
  public resourceUrl = SERVER_API_URL + 'api/poker-profiles';

  constructor(protected http: HttpClient) {}

  create(pokerProfile: IPokerProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pokerProfile);
    return this.http
      .post<IPokerProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pokerProfile: IPokerProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pokerProfile);
    return this.http
      .put<IPokerProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPokerProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPokerProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pokerProfile: IPokerProfile): IPokerProfile {
    const copy: IPokerProfile = Object.assign({}, pokerProfile, {
      regDate: pokerProfile.regDate && pokerProfile.regDate.isValid() ? pokerProfile.regDate.format(DATE_FORMAT) : undefined,
      lastDate: pokerProfile.lastDate && pokerProfile.lastDate.isValid() ? pokerProfile.lastDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.regDate = res.body.regDate ? moment(res.body.regDate) : undefined;
      res.body.lastDate = res.body.lastDate ? moment(res.body.lastDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pokerProfile: IPokerProfile) => {
        pokerProfile.regDate = pokerProfile.regDate ? moment(pokerProfile.regDate) : undefined;
        pokerProfile.lastDate = pokerProfile.lastDate ? moment(pokerProfile.lastDate) : undefined;
      });
    }
    return res;
  }
}
