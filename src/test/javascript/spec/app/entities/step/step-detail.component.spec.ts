/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CronusTestModule } from '../../../test.module';
import { StepDetailComponent } from '../../../../../../main/webapp/app/entities/step/step-detail.component';
import { StepService } from '../../../../../../main/webapp/app/entities/step/step.service';
import { Step } from '../../../../../../main/webapp/app/entities/step/step.model';

describe('Component Tests', () => {

    describe('Step Management Detail Component', () => {
        let comp: StepDetailComponent;
        let fixture: ComponentFixture<StepDetailComponent>;
        let service: StepService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CronusTestModule],
                declarations: [StepDetailComponent],
                providers: [
                    StepService
                ]
            })
            .overrideTemplate(StepDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StepDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StepService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Step(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.step).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
