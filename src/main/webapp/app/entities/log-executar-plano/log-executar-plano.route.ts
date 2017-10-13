import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LogExecutarPlanoComponent } from './log-executar-plano.component';
import { LogExecutarPlanoDetailComponent } from './log-executar-plano-detail.component';
import { LogExecutarPlanoPopupComponent } from './log-executar-plano-dialog.component';
import { LogExecutarPlanoDeletePopupComponent } from './log-executar-plano-delete-dialog.component';

export const logExecutarPlanoRoute: Routes = [
    {
        path: 'log-executar-plano',
        component: LogExecutarPlanoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.logExecutarPlano.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'log-executar-plano/:id',
        component: LogExecutarPlanoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.logExecutarPlano.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logExecutarPlanoPopupRoute: Routes = [
    {
        path: 'log-executar-plano-new',
        component: LogExecutarPlanoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.logExecutarPlano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-executar-plano/:id/edit',
        component: LogExecutarPlanoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.logExecutarPlano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-executar-plano/:id/delete',
        component: LogExecutarPlanoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.logExecutarPlano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
