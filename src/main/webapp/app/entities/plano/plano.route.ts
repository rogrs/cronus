import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlanoComponent } from './plano.component';
import { PlanoDetailComponent } from './plano-detail.component';
import { PlanoPopupComponent } from './plano-dialog.component';
import { PlanoDeletePopupComponent } from './plano-delete-dialog.component';

export const planoRoute: Routes = [
    {
        path: 'plano',
        component: PlanoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plano.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plano/:id',
        component: PlanoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plano.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planoPopupRoute: Routes = [
    {
        path: 'plano-new',
        component: PlanoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plano/:id/edit',
        component: PlanoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plano/:id/delete',
        component: PlanoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plano.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
