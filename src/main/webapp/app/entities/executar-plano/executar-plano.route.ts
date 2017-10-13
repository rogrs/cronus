import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExecutarPlanoComponent } from './executar-plano.component';
import { ExecutarPlanoDetailComponent } from './executar-plano-detail.component';
import { ExecutarPlanoPopupComponent } from './executar-plano-dialog.component';
import { ExecutarPlanoDeletePopupComponent } from './executar-plano-delete-dialog.component';

export const executarPlanoRoute: Routes = [
    {
        path: 'executar-plano',
        component: ExecutarPlanoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.executarPlano.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'executar-plano/:id',
        component: ExecutarPlanoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.executarPlano.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const executarPlanoPopupRoute: Routes = [
    {
        path: 'executar-plano-new',
        component: ExecutarPlanoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.executarPlano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'executar-plano/:id/edit',
        component: ExecutarPlanoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.executarPlano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'executar-plano/:id/delete',
        component: ExecutarPlanoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.executarPlano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
