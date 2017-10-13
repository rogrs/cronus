import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogExecutarPlano } from './log-executar-plano.model';
import { LogExecutarPlanoPopupService } from './log-executar-plano-popup.service';
import { LogExecutarPlanoService } from './log-executar-plano.service';

@Component({
    selector: 'jhi-log-executar-plano-delete-dialog',
    templateUrl: './log-executar-plano-delete-dialog.component.html'
})
export class LogExecutarPlanoDeleteDialogComponent {

    logExecutarPlano: LogExecutarPlano;

    constructor(
        private logExecutarPlanoService: LogExecutarPlanoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logExecutarPlanoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'logExecutarPlanoListModification',
                content: 'Deleted an logExecutarPlano'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-log-executar-plano-delete-popup',
    template: ''
})
export class LogExecutarPlanoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logExecutarPlanoPopupService: LogExecutarPlanoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.logExecutarPlanoPopupService
                .open(LogExecutarPlanoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
