import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Atividade } from './atividade.model';
import { AtividadePopupService } from './atividade-popup.service';
import { AtividadeService } from './atividade.service';
import { Plano, PlanoService } from '../plano';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-atividade-dialog',
    templateUrl: './atividade-dialog.component.html'
})
export class AtividadeDialogComponent implements OnInit {

    atividade: Atividade;
    isSaving: boolean;

    planos: Plano[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private atividadeService: AtividadeService,
        private planoService: PlanoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.planoService.query()
            .subscribe((res: ResponseWrapper) => { this.planos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.atividade.id !== undefined) {
            this.subscribeToSaveResponse(
                this.atividadeService.update(this.atividade));
        } else {
            this.subscribeToSaveResponse(
                this.atividadeService.create(this.atividade));
        }
    }

    private subscribeToSaveResponse(result: Observable<Atividade>) {
        result.subscribe((res: Atividade) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Atividade) {
        this.eventManager.broadcast({ name: 'atividadeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPlanoById(index: number, item: Plano) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-atividade-popup',
    template: ''
})
export class AtividadePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private atividadePopupService: AtividadePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.atividadePopupService
                    .open(AtividadeDialogComponent as Component, params['id']);
            } else {
                this.atividadePopupService
                    .open(AtividadeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
