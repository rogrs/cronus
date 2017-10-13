/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AtividadeDetailComponent } from '../../../../../../main/webapp/app/entities/atividade/atividade-detail.component';
import { AtividadeService } from '../../../../../../main/webapp/app/entities/atividade/atividade.service';
import { Atividade } from '../../../../../../main/webapp/app/entities/atividade/atividade.model';

describe('Component Tests', () => {

    describe('Atividade Management Detail Component', () => {
        let comp: AtividadeDetailComponent;
        let fixture: ComponentFixture<AtividadeDetailComponent>;
        let service: AtividadeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [AtividadeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AtividadeService,
                    JhiEventManager
                ]
            }).overrideTemplate(AtividadeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AtividadeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AtividadeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Atividade(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.atividade).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
