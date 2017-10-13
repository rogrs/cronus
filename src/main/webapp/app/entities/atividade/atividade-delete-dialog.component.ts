import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Atividade } from './atividade.model';
import { AtividadePopupService } from './atividade-popup.service';
import { AtividadeService } from './atividade.service';

@Component({
    selector: 'jhi-atividade-delete-dialog',
    templateUrl: './atividade-delete-dialog.component.html'
})
export class AtividadeDeleteDialogComponent {

    atividade: Atividade;

    constructor(
        private atividadeService: AtividadeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.atividadeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'atividadeListModification',
                content: 'Deleted an atividade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-atividade-delete-popup',
    template: ''
})
export class AtividadeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private atividadePopupService: AtividadePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.atividadePopupService
                .open(AtividadeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
