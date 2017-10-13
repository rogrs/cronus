import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Plugin } from './plugin.model';
import { PluginPopupService } from './plugin-popup.service';
import { PluginService } from './plugin.service';

@Component({
    selector: 'jhi-plugin-dialog',
    templateUrl: './plugin-dialog.component.html'
})
export class PluginDialogComponent implements OnInit {

    plugin: Plugin;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pluginService: PluginService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plugin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pluginService.update(this.plugin));
        } else {
            this.subscribeToSaveResponse(
                this.pluginService.create(this.plugin));
        }
    }

    private subscribeToSaveResponse(result: Observable<Plugin>) {
        result.subscribe((res: Plugin) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Plugin) {
        this.eventManager.broadcast({ name: 'pluginListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-plugin-popup',
    template: ''
})
export class PluginPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pluginPopupService: PluginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pluginPopupService
                    .open(PluginDialogComponent as Component, params['id']);
            } else {
                this.pluginPopupService
                    .open(PluginDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
