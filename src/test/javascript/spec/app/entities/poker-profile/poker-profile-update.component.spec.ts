import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PokerProfileUpdateComponent } from 'app/entities/poker-profile/poker-profile-update.component';
import { PokerProfileService } from 'app/entities/poker-profile/poker-profile.service';
import { PokerProfile } from 'app/shared/model/poker-profile.model';

describe('Component Tests', () => {
  describe('PokerProfile Management Update Component', () => {
    let comp: PokerProfileUpdateComponent;
    let fixture: ComponentFixture<PokerProfileUpdateComponent>;
    let service: PokerProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PokerProfileUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PokerProfileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PokerProfileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PokerProfileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PokerProfile(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PokerProfile();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
