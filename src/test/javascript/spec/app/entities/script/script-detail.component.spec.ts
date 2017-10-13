/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ScriptDetailComponent } from '../../../../../../main/webapp/app/entities/script/script-detail.component';
import { ScriptService } from '../../../../../../main/webapp/app/entities/script/script.service';
import { Script } from '../../../../../../main/webapp/app/entities/script/script.model';

describe('Component Tests', () => {

    describe('Script Management Detail Component', () => {
        let comp: ScriptDetailComponent;
        let fixture: ComponentFixture<ScriptDetailComponent>;
        let service: ScriptService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [ScriptDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ScriptService,
                    JhiEventManager
                ]
            }).overrideTemplate(ScriptDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ScriptDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScriptService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Script(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.script).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
