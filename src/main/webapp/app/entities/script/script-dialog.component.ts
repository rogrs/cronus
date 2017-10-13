import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Script } from './script.model';
import { ScriptPopupService } from './script-popup.service';
import { ScriptService } from './script.service';
import { Plugin, PluginService } from '../plugin';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-script-dialog',
    templateUrl: './script-dialog.component.html'
})
export class ScriptDialogComponent implements OnInit {

    script: Script;
    isSaving: boolean;

    plugins: Plugin[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private scriptService: ScriptService,
        private pluginService: PluginService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pluginService.query()
            .subscribe((res: ResponseWrapper) => { this.plugins = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.script.id !== undefined) {
            this.subscribeToSaveResponse(
                this.scriptService.update(this.script));
        } else {
            this.subscribeToSaveResponse(
                this.scriptService.create(this.script));
        }
    }

    private subscribeToSaveResponse(result: Observable<Script>) {
        result.subscribe((res: Script) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Script) {
        this.eventManager.broadcast({ name: 'scriptListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPluginById(index: number, item: Plugin) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-script-popup',
    template: ''
})
export class ScriptPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private scriptPopupService: ScriptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.scriptPopupService
                    .open(ScriptDialogComponent as Component, params['id']);
            } else {
                this.scriptPopupService
                    .open(ScriptDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
