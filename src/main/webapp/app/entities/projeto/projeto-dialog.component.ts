import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Projeto } from './projeto.model';
import { ProjetoPopupService } from './projeto-popup.service';
import { ProjetoService } from './projeto.service';

@Component({
    selector: 'jhi-projeto-dialog',
    templateUrl: './projeto-dialog.component.html'
})
export class ProjetoDialogComponent implements OnInit {

    projeto: Projeto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private projetoService: ProjetoService,
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
        if (this.projeto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projetoService.update(this.projeto));
        } else {
            this.subscribeToSaveResponse(
                this.projetoService.create(this.projeto));
        }
    }

    private subscribeToSaveResponse(result: Observable<Projeto>) {
        result.subscribe((res: Projeto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Projeto) {
        this.eventManager.broadcast({ name: 'projetoListModification', content: 'OK'});
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
    selector: 'jhi-projeto-popup',
    template: ''
})
export class ProjetoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projetoPopupService: ProjetoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projetoPopupService
                    .open(ProjetoDialogComponent as Component, params['id']);
            } else {
                this.projetoPopupService
                    .open(ProjetoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
