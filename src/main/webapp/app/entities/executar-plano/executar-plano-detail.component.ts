import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ExecutarPlano } from './executar-plano.model';
import { ExecutarPlanoService } from './executar-plano.service';

@Component({
    selector: 'jhi-executar-plano-detail',
    templateUrl: './executar-plano-detail.component.html'
})
export class ExecutarPlanoDetailComponent implements OnInit, OnDestroy {

    executarPlano: ExecutarPlano;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private executarPlanoService: ExecutarPlanoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExecutarPlanos();
    }

    load(id) {
        this.executarPlanoService.find(id).subscribe((executarPlano) => {
            this.executarPlano = executarPlano;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExecutarPlanos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'executarPlanoListModification',
            (response) => this.load(this.executarPlano.id)
        );
    }
}
