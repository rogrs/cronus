import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutobotSharedModule } from '../../shared';
import {
    LogExecutarPlanoService,
    LogExecutarPlanoPopupService,
    LogExecutarPlanoComponent,
    LogExecutarPlanoDetailComponent,
    LogExecutarPlanoDialogComponent,
    LogExecutarPlanoPopupComponent,
    LogExecutarPlanoDeletePopupComponent,
    LogExecutarPlanoDeleteDialogComponent,
    logExecutarPlanoRoute,
    logExecutarPlanoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...logExecutarPlanoRoute,
    ...logExecutarPlanoPopupRoute,
];

@NgModule({
    imports: [
        AutobotSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LogExecutarPlanoComponent,
        LogExecutarPlanoDetailComponent,
        LogExecutarPlanoDialogComponent,
        LogExecutarPlanoDeleteDialogComponent,
        LogExecutarPlanoPopupComponent,
        LogExecutarPlanoDeletePopupComponent,
    ],
    entryComponents: [
        LogExecutarPlanoComponent,
        LogExecutarPlanoDialogComponent,
        LogExecutarPlanoPopupComponent,
        LogExecutarPlanoDeleteDialogComponent,
        LogExecutarPlanoDeletePopupComponent,
    ],
    providers: [
        LogExecutarPlanoService,
        LogExecutarPlanoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotLogExecutarPlanoModule {}
