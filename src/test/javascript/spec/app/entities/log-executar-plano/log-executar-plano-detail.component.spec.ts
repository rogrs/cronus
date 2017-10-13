/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LogExecutarPlanoDetailComponent } from '../../../../../../main/webapp/app/entities/log-executar-plano/log-executar-plano-detail.component';
import { LogExecutarPlanoService } from '../../../../../../main/webapp/app/entities/log-executar-plano/log-executar-plano.service';
import { LogExecutarPlano } from '../../../../../../main/webapp/app/entities/log-executar-plano/log-executar-plano.model';

describe('Component Tests', () => {

    describe('LogExecutarPlano Management Detail Component', () => {
        let comp: LogExecutarPlanoDetailComponent;
        let fixture: ComponentFixture<LogExecutarPlanoDetailComponent>;
        let service: LogExecutarPlanoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [LogExecutarPlanoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LogExecutarPlanoService,
                    JhiEventManager
                ]
            }).overrideTemplate(LogExecutarPlanoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogExecutarPlanoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogExecutarPlanoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LogExecutarPlano(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.logExecutarPlano).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
