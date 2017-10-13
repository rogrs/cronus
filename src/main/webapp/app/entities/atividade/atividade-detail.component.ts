import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Atividade } from './atividade.model';
import { AtividadeService } from './atividade.service';

@Component({
    selector: 'jhi-atividade-detail',
    templateUrl: './atividade-detail.component.html'
})
export class AtividadeDetailComponent implements OnInit, OnDestroy {

    atividade: Atividade;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private atividadeService: AtividadeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAtividades();
    }

    load(id) {
        this.atividadeService.find(id).subscribe((atividade) => {
            this.atividade = atividade;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAtividades() {
        this.eventSubscriber = this.eventManager.subscribe(
            'atividadeListModification',
            (response) => this.load(this.atividade.id)
        );
    }
}
