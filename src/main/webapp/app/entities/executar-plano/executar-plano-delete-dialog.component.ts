import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExecutarPlano } from './executar-plano.model';
import { ExecutarPlanoPopupService } from './executar-plano-popup.service';
import { ExecutarPlanoService } from './executar-plano.service';

@Component({
    selector: 'jhi-executar-plano-delete-dialog',
    templateUrl: './executar-plano-delete-dialog.component.html'
})
export class ExecutarPlanoDeleteDialogComponent {

    executarPlano: ExecutarPlano;

    constructor(
        private executarPlanoService: ExecutarPlanoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.executarPlanoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'executarPlanoListModification',
                content: 'Deleted an executarPlano'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-executar-plano-delete-popup',
    template: ''
})
export class ExecutarPlanoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private executarPlanoPopupService: ExecutarPlanoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.executarPlanoPopupService
                .open(ExecutarPlanoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
