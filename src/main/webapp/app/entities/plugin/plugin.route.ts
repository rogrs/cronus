import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PluginComponent } from './plugin.component';
import { PluginDetailComponent } from './plugin-detail.component';
import { PluginPopupComponent } from './plugin-dialog.component';
import { PluginDeletePopupComponent } from './plugin-delete-dialog.component';

export const pluginRoute: Routes = [
    {
        path: 'plugin',
        component: PluginComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plugin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plugin/:id',
        component: PluginDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plugin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pluginPopupRoute: Routes = [
    {
        path: 'plugin-new',
        component: PluginPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plugin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plugin/:id/edit',
        component: PluginPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plugin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plugin/:id/delete',
        component: PluginDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autobotApp.plugin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
