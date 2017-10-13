import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Script } from './script.model';
import { ScriptService } from './script.service';

@Component({
    selector: 'jhi-script-detail',
    templateUrl: './script-detail.component.html'
})
export class ScriptDetailComponent implements OnInit, OnDestroy {

    script: Script;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private scriptService: ScriptService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInScripts();
    }

    load(id) {
        this.scriptService.find(id).subscribe((script) => {
            this.script = script;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInScripts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'scriptListModification',
            (response) => this.load(this.script.id)
        );
    }
}
