import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CronusSharedModule } from '../../shared';
import {
    StepService,
    StepPopupService,
    StepComponent,
    StepDetailComponent,
    StepDialogComponent,
    StepPopupComponent,
    StepDeletePopupComponent,
    StepDeleteDialogComponent,
    stepRoute,
    stepPopupRoute,
} from './';

const ENTITY_STATES = [
    ...stepRoute,
    ...stepPopupRoute,
];

@NgModule({
    imports: [
        CronusSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StepComponent,
        StepDetailComponent,
        StepDialogComponent,
        StepDeleteDialogComponent,
        StepPopupComponent,
        StepDeletePopupComponent,
    ],
    entryComponents: [
        StepComponent,
        StepDialogComponent,
        StepPopupComponent,
        StepDeleteDialogComponent,
        StepDeletePopupComponent,
    ],
    providers: [
        StepService,
        StepPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CronusStepModule {}
