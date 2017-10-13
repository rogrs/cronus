import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Plano } from './plano.model';
import { PlanoPopupService } from './plano-popup.service';
import { PlanoService } from './plano.service';

@Component({
    selector: 'jhi-plano-delete-dialog',
    templateUrl: './plano-delete-dialog.component.html'
})
export class PlanoDeleteDialogComponent {

    plano: Plano;

    constructor(
        private planoService: PlanoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'planoListModification',
                content: 'Deleted an plano'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plano-delete-popup',
    template: ''
})
export class PlanoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planoPopupService: PlanoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.planoPopupService
                .open(PlanoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
