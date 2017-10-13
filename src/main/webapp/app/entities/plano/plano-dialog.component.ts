import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Plano } from './plano.model';
import { PlanoPopupService } from './plano-popup.service';
import { PlanoService } from './plano.service';
import { Projeto, ProjetoService } from '../projeto';
import { Script, ScriptService } from '../script';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-plano-dialog',
    templateUrl: './plano-dialog.component.html'
})
export class PlanoDialogComponent implements OnInit {

    plano: Plano;
    isSaving: boolean;

    projetos: Projeto[];

    scripts: Script[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private planoService: PlanoService,
        private projetoService: ProjetoService,
        private scriptService: ScriptService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projetoService.query()
            .subscribe((res: ResponseWrapper) => { this.projetos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.scriptService.query()
            .subscribe((res: ResponseWrapper) => { this.scripts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plano.id !== undefined) {
            this.subscribeToSaveResponse(
                this.planoService.update(this.plano));
        } else {
            this.subscribeToSaveResponse(
                this.planoService.create(this.plano));
        }
    }

    private subscribeToSaveResponse(result: Observable<Plano>) {
        result.subscribe((res: Plano) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Plano) {
        this.eventManager.broadcast({ name: 'planoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjetoById(index: number, item: Projeto) {
        return item.id;
    }

    trackScriptById(index: number, item: Script) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-plano-popup',
    template: ''
})
export class PlanoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planoPopupService: PlanoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.planoPopupService
                    .open(PlanoDialogComponent as Component, params['id']);
            } else {
                this.planoPopupService
                    .open(PlanoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
