import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutobotSharedModule } from '../../shared';
import {
    ProjetoService,
    ProjetoPopupService,
    ProjetoComponent,
    ProjetoDetailComponent,
    ProjetoDialogComponent,
    ProjetoPopupComponent,
    ProjetoDeletePopupComponent,
    ProjetoDeleteDialogComponent,
    projetoRoute,
    projetoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...projetoRoute,
    ...projetoPopupRoute,
];

@NgModule({
    imports: [
        AutobotSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProjetoComponent,
        ProjetoDetailComponent,
        ProjetoDialogComponent,
        ProjetoDeleteDialogComponent,
        ProjetoPopupComponent,
        ProjetoDeletePopupComponent,
    ],
    entryComponents: [
        ProjetoComponent,
        ProjetoDialogComponent,
        ProjetoPopupComponent,
        ProjetoDeleteDialogComponent,
        ProjetoDeletePopupComponent,
    ],
    providers: [
        ProjetoService,
        ProjetoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotProjetoModule {}
