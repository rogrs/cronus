import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutobotSharedModule } from '../../shared';
import {
    AtividadeService,
    AtividadePopupService,
    AtividadeComponent,
    AtividadeDetailComponent,
    AtividadeDialogComponent,
    AtividadePopupComponent,
    AtividadeDeletePopupComponent,
    AtividadeDeleteDialogComponent,
    atividadeRoute,
    atividadePopupRoute,
} from './';

const ENTITY_STATES = [
    ...atividadeRoute,
    ...atividadePopupRoute,
];

@NgModule({
    imports: [
        AutobotSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AtividadeComponent,
        AtividadeDetailComponent,
        AtividadeDialogComponent,
        AtividadeDeleteDialogComponent,
        AtividadePopupComponent,
        AtividadeDeletePopupComponent,
    ],
    entryComponents: [
        AtividadeComponent,
        AtividadeDialogComponent,
        AtividadePopupComponent,
        AtividadeDeleteDialogComponent,
        AtividadeDeletePopupComponent,
    ],
    providers: [
        AtividadeService,
        AtividadePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotAtividadeModule {}
