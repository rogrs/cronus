/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutobotTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PluginDetailComponent } from '../../../../../../main/webapp/app/entities/plugin/plugin-detail.component';
import { PluginService } from '../../../../../../main/webapp/app/entities/plugin/plugin.service';
import { Plugin } from '../../../../../../main/webapp/app/entities/plugin/plugin.model';

describe('Component Tests', () => {

    describe('Plugin Management Detail Component', () => {
        let comp: PluginDetailComponent;
        let fixture: ComponentFixture<PluginDetailComponent>;
        let service: PluginService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutobotTestModule],
                declarations: [PluginDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PluginService,
                    JhiEventManager
                ]
            }).overrideTemplate(PluginDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PluginDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PluginService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Plugin(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plugin).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
