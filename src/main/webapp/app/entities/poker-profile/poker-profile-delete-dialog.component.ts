import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPokerProfile } from 'app/shared/model/poker-profile.model';
import { PokerProfileService } from './poker-profile.service';

@Component({
  templateUrl: './poker-profile-delete-dialog.component.html',
})
export class PokerProfileDeleteDialogComponent {
  pokerProfile?: IPokerProfile;

  constructor(
    protected pokerProfileService: PokerProfileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pokerProfileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pokerProfileListModification');
      this.activeModal.close();
    });
  }
}
