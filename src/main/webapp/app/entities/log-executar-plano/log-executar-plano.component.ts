import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { LogExecutarPlano } from './log-executar-plano.model';
import { LogExecutarPlanoService } from './log-executar-plano.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-log-executar-plano',
    templateUrl: './log-executar-plano.component.html'
})
export class LogExecutarPlanoComponent implements OnInit, OnDestroy {
logExecutarPlanos: LogExecutarPlano[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private logExecutarPlanoService: LogExecutarPlanoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.logExecutarPlanoService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.logExecutarPlanos = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.logExecutarPlanoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.logExecutarPlanos = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLogExecutarPlanos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LogExecutarPlano) {
        return item.id;
    }
    registerChangeInLogExecutarPlanos() {
        this.eventSubscriber = this.eventManager.subscribe('logExecutarPlanoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
