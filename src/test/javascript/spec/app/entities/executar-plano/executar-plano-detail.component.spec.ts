/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExecutarPlanoDetailComponent } from '../../../../../../main/webapp/app/entities/executar-plano/executar-plano-detail.component';
import { ExecutarPlanoService } from '../../../../../../main/webapp/app/entities/executar-plano/executar-plano.service';
import { ExecutarPlano } from '../../../../../../main/webapp/app/entities/executar-plano/executar-plano.model';

describe('Component Tests', () => {

    describe('ExecutarPlano Management Detail Component', () => {
        let comp: ExecutarPlanoDetailComponent;
        let fixture: ComponentFixture<ExecutarPlanoDetailComponent>;
        let service: ExecutarPlanoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [ExecutarPlanoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExecutarPlanoService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExecutarPlanoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExecutarPlanoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExecutarPlanoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ExecutarPlano(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.executarPlano).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
