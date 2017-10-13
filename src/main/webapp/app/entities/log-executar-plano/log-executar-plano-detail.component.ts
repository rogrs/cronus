import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LogExecutarPlano } from './log-executar-plano.model';
import { LogExecutarPlanoService } from './log-executar-plano.service';

@Component({
    selector: 'jhi-log-executar-plano-detail',
    templateUrl: './log-executar-plano-detail.component.html'
})
export class LogExecutarPlanoDetailComponent implements OnInit, OnDestroy {

    logExecutarPlano: LogExecutarPlano;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private logExecutarPlanoService: LogExecutarPlanoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogExecutarPlanos();
    }

    load(id) {
        this.logExecutarPlanoService.find(id).subscribe((logExecutarPlano) => {
            this.logExecutarPlano = logExecutarPlano;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogExecutarPlanos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'logExecutarPlanoListModification',
            (response) => this.load(this.logExecutarPlano.id)
        );
    }
}
