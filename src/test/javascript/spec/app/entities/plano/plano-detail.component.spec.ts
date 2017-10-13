/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlanoDetailComponent } from '../../../../../../main/webapp/app/entities/plano/plano-detail.component';
import { PlanoService } from '../../../../../../main/webapp/app/entities/plano/plano.service';
import { Plano } from '../../../../../../main/webapp/app/entities/plano/plano.model';

describe('Component Tests', () => {

    describe('Plano Management Detail Component', () => {
        let comp: PlanoDetailComponent;
        let fixture: ComponentFixture<PlanoDetailComponent>;
        let service: PlanoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [PlanoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlanoService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlanoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlanoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Plano(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plano).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
