import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProjetoComponent } from './projeto.component';
import { ProjetoDetailComponent } from './projeto-detail.component';
import { ProjetoPopupComponent } from './projeto-dialog.component';
import { ProjetoDeletePopupComponent } from './projeto-delete-dialog.component';

export const projetoRoute: Routes = [
    {
        path: 'projeto',
        component: ProjetoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.projeto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'projeto/:id',
        component: ProjetoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.projeto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projetoPopupRoute: Routes = [
    {
        path: 'projeto-new',
        component: ProjetoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.projeto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projeto/:id/edit',
        component: ProjetoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.projeto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projeto/:id/delete',
        component: ProjetoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.projeto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
