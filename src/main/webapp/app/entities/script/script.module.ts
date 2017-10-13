import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutobotSharedModule } from '../../shared';
import {
    ScriptService,
    ScriptPopupService,
    ScriptComponent,
    ScriptDetailComponent,
    ScriptDialogComponent,
    ScriptPopupComponent,
    ScriptDeletePopupComponent,
    ScriptDeleteDialogComponent,
    scriptRoute,
    scriptPopupRoute,
} from './';

const ENTITY_STATES = [
    ...scriptRoute,
    ...scriptPopupRoute,
];

@NgModule({
    imports: [
        AutobotSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ScriptComponent,
        ScriptDetailComponent,
        ScriptDialogComponent,
        ScriptDeleteDialogComponent,
        ScriptPopupComponent,
        ScriptDeletePopupComponent,
    ],
    entryComponents: [
        ScriptComponent,
        ScriptDialogComponent,
        ScriptPopupComponent,
        ScriptDeleteDialogComponent,
        ScriptDeletePopupComponent,
    ],
    providers: [
        ScriptService,
        ScriptPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotScriptModule {}
