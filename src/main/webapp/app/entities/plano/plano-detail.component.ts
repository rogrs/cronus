import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Plano } from './plano.model';
import { PlanoService } from './plano.service';

@Component({
    selector: 'jhi-plano-detail',
    templateUrl: './plano-detail.component.html'
})
export class PlanoDetailComponent implements OnInit, OnDestroy {

    plano: Plano;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private planoService: PlanoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlanos();
    }

    load(id) {
        this.planoService.find(id).subscribe((plano) => {
            this.plano = plano;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlanos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'planoListModification',
            (response) => this.load(this.plano.id)
        );
    }
}
