import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutobotSharedModule } from '../../shared';
import {
    ExecutarPlanoService,
    ExecutarPlanoPopupService,
    ExecutarPlanoComponent,
    ExecutarPlanoDetailComponent,
    ExecutarPlanoDialogComponent,
    ExecutarPlanoPopupComponent,
    ExecutarPlanoDeletePopupComponent,
    ExecutarPlanoDeleteDialogComponent,
    executarPlanoRoute,
    executarPlanoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...executarPlanoRoute,
    ...executarPlanoPopupRoute,
];

@NgModule({
    imports: [
        AutobotSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExecutarPlanoComponent,
        ExecutarPlanoDetailComponent,
        ExecutarPlanoDialogComponent,
        ExecutarPlanoDeleteDialogComponent,
        ExecutarPlanoPopupComponent,
        ExecutarPlanoDeletePopupComponent,
    ],
    entryComponents: [
        ExecutarPlanoComponent,
        ExecutarPlanoDialogComponent,
        ExecutarPlanoPopupComponent,
        ExecutarPlanoDeleteDialogComponent,
        ExecutarPlanoDeletePopupComponent,
    ],
    providers: [
        ExecutarPlanoService,
        ExecutarPlanoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotExecutarPlanoModule {}
