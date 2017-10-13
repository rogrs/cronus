import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExecutarPlano } from './executar-plano.model';
import { ExecutarPlanoPopupService } from './executar-plano-popup.service';
import { ExecutarPlanoService } from './executar-plano.service';
import { Plano, PlanoService } from '../plano';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-executar-plano-dialog',
    templateUrl: './executar-plano-dialog.component.html'
})
export class ExecutarPlanoDialogComponent implements OnInit {

    executarPlano: ExecutarPlano;
    isSaving: boolean;

    planos: Plano[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private executarPlanoService: ExecutarPlanoService,
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
        if (this.executarPlano.id !== undefined) {
            this.subscribeToSaveResponse(
                this.executarPlanoService.update(this.executarPlano));
        } else {
            this.subscribeToSaveResponse(
                this.executarPlanoService.create(this.executarPlano));
        }
    }

    private subscribeToSaveResponse(result: Observable<ExecutarPlano>) {
        result.subscribe((res: ExecutarPlano) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ExecutarPlano) {
        this.eventManager.broadcast({ name: 'executarPlanoListModification', content: 'OK'});
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
    selector: 'jhi-executar-plano-popup',
    template: ''
})
export class ExecutarPlanoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private executarPlanoPopupService: ExecutarPlanoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.executarPlanoPopupService
                    .open(ExecutarPlanoDialogComponent as Component, params['id']);
            } else {
                this.executarPlanoPopupService
                    .open(ExecutarPlanoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
