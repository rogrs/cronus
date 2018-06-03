import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StepComponent } from './step.component';
import { StepDetailComponent } from './step-detail.component';
import { StepPopupComponent } from './step-dialog.component';
import { StepDeletePopupComponent } from './step-delete-dialog.component';

export const stepRoute: Routes = [
    {
        path: 'step',
        component: StepComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cronusApp.step.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'step/:id',
        component: StepDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cronusApp.step.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stepPopupRoute: Routes = [
    {
        path: 'step-new',
        component: StepPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cronusApp.step.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'step/:id/edit',
        component: StepPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cronusApp.step.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'step/:id/delete',
        component: StepDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cronusApp.step.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
