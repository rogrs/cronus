import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Plugin } from './plugin.model';
import { PluginPopupService } from './plugin-popup.service';
import { PluginService } from './plugin.service';

@Component({
    selector: 'jhi-plugin-delete-dialog',
    templateUrl: './plugin-delete-dialog.component.html'
})
export class PluginDeleteDialogComponent {

    plugin: Plugin;

    constructor(
        private pluginService: PluginService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pluginService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pluginListModification',
                content: 'Deleted an plugin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plugin-delete-popup',
    template: ''
})
export class PluginDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pluginPopupService: PluginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pluginPopupService
                .open(PluginDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
