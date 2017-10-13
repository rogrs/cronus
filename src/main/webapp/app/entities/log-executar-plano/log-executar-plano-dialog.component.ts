import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogExecutarPlano } from './log-executar-plano.model';
import { LogExecutarPlanoPopupService } from './log-executar-plano-popup.service';
import { LogExecutarPlanoService } from './log-executar-plano.service';
import { ExecutarPlano, ExecutarPlanoService } from '../executar-plano';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-log-executar-plano-dialog',
    templateUrl: './log-executar-plano-dialog.component.html'
})
export class LogExecutarPlanoDialogComponent implements OnInit {

    logExecutarPlano: LogExecutarPlano;
    isSaving: boolean;

    executarplanos: ExecutarPlano[];
    criadoDp: any;
    finalizadoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private logExecutarPlanoService: LogExecutarPlanoService,
        private executarPlanoService: ExecutarPlanoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.executarPlanoService.query()
            .subscribe((res: ResponseWrapper) => { this.executarplanos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.logExecutarPlano.id !== undefined) {
            this.subscribeToSaveResponse(
                this.logExecutarPlanoService.update(this.logExecutarPlano));
        } else {
            this.subscribeToSaveResponse(
                this.logExecutarPlanoService.create(this.logExecutarPlano));
        }
    }

    private subscribeToSaveResponse(result: Observable<LogExecutarPlano>) {
        result.subscribe((res: LogExecutarPlano) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LogExecutarPlano) {
        this.eventManager.broadcast({ name: 'logExecutarPlanoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackExecutarPlanoById(index: number, item: ExecutarPlano) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-log-executar-plano-popup',
    template: ''
})
export class LogExecutarPlanoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logExecutarPlanoPopupService: LogExecutarPlanoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.logExecutarPlanoPopupService
                    .open(LogExecutarPlanoDialogComponent as Component, params['id']);
            } else {
                this.logExecutarPlanoPopupService
                    .open(LogExecutarPlanoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
