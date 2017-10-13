/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProjetoDetailComponent } from '../../../../../../main/webapp/app/entities/projeto/projeto-detail.component';
import { ProjetoService } from '../../../../../../main/webapp/app/entities/projeto/projeto.service';
import { Projeto } from '../../../../../../main/webapp/app/entities/projeto/projeto.model';

describe('Component Tests', () => {

    describe('Projeto Management Detail Component', () => {
        let comp: ProjetoDetailComponent;
        let fixture: ComponentFixture<ProjetoDetailComponent>;
        let service: ProjetoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [ProjetoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProjetoService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProjetoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjetoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjetoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Projeto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.projeto).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
