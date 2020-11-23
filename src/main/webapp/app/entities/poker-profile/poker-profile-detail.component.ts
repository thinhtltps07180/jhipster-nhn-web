import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPokerProfile } from 'app/shared/model/poker-profile.model';

@Component({
  selector: 'jhi-poker-profile-detail',
  templateUrl: './poker-profile-detail.component.html',
})
export class PokerProfileDetailComponent implements OnInit {
  pokerProfile: IPokerProfile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pokerProfile }) => (this.pokerProfile = pokerProfile));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
