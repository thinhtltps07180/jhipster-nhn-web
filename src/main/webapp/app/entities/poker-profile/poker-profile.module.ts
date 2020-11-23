import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { PokerProfileComponent } from './poker-profile.component';
import { PokerProfileDetailComponent } from './poker-profile-detail.component';
import { PokerProfileUpdateComponent } from './poker-profile-update.component';
import { PokerProfileDeleteDialogComponent } from './poker-profile-delete-dialog.component';
import { pokerProfileRoute } from './poker-profile.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(pokerProfileRoute)],
  declarations: [PokerProfileComponent, PokerProfileDetailComponent, PokerProfileUpdateComponent, PokerProfileDeleteDialogComponent],
  entryComponents: [PokerProfileDeleteDialogComponent],
})
export class JhipsterPokerProfileModule {}
