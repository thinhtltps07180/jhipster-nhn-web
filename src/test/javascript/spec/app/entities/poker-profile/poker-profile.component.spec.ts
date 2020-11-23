import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { PokerProfileComponent } from 'app/entities/poker-profile/poker-profile.component';
import { PokerProfileService } from 'app/entities/poker-profile/poker-profile.service';
import { PokerProfile } from 'app/shared/model/poker-profile.model';

describe('Component Tests', () => {
  describe('PokerProfile Management Component', () => {
    let comp: PokerProfileComponent;
    let fixture: ComponentFixture<PokerProfileComponent>;
    let service: PokerProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PokerProfileComponent],
      })
        .overrideTemplate(PokerProfileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PokerProfileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PokerProfileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PokerProfile(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pokerProfiles && comp.pokerProfiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
