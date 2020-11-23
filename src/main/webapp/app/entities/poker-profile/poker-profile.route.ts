import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPokerProfile, PokerProfile } from 'app/shared/model/poker-profile.model';
import { PokerProfileService } from './poker-profile.service';
import { PokerProfileComponent } from './poker-profile.component';
import { PokerProfileDetailComponent } from './poker-profile-detail.component';
import { PokerProfileUpdateComponent } from './poker-profile-update.component';

@Injectable({ providedIn: 'root' })
export class PokerProfileResolve implements Resolve<IPokerProfile> {
  constructor(private service: PokerProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPokerProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pokerProfile: HttpResponse<PokerProfile>) => {
          if (pokerProfile.body) {
            return of(pokerProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PokerProfile());
  }
}

export const pokerProfileRoute: Routes = [
  {
    path: '',
    component: PokerProfileComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pokerProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PokerProfileDetailComponent,
    resolve: {
      pokerProfile: PokerProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pokerProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PokerProfileUpdateComponent,
    resolve: {
      pokerProfile: PokerProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pokerProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PokerProfileUpdateComponent,
    resolve: {
      pokerProfile: PokerProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pokerProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
