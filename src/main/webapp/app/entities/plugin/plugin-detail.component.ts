import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Plugin } from './plugin.model';
import { PluginService } from './plugin.service';

@Component({
    selector: 'jhi-plugin-detail',
    templateUrl: './plugin-detail.component.html'
})
export class PluginDetailComponent implements OnInit, OnDestroy {

    plugin: Plugin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pluginService: PluginService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlugins();
    }

    load(id) {
        this.pluginService.find(id).subscribe((plugin) => {
            this.plugin = plugin;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlugins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pluginListModification',
            (response) => this.load(this.plugin.id)
        );
    }
}
