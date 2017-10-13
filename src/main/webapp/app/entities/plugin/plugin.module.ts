import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutobotSharedModule } from '../../shared';
import {
    PluginService,
    PluginPopupService,
    PluginComponent,
    PluginDetailComponent,
    PluginDialogComponent,
    PluginPopupComponent,
    PluginDeletePopupComponent,
    PluginDeleteDialogComponent,
    pluginRoute,
    pluginPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pluginRoute,
    ...pluginPopupRoute,
];

@NgModule({
    imports: [
        AutobotSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PluginComponent,
        PluginDetailComponent,
        PluginDialogComponent,
        PluginDeleteDialogComponent,
        PluginPopupComponent,
        PluginDeletePopupComponent,
    ],
    entryComponents: [
        PluginComponent,
        PluginDialogComponent,
        PluginPopupComponent,
        PluginDeleteDialogComponent,
        PluginDeletePopupComponent,
    ],
    providers: [
        PluginService,
        PluginPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotPluginModule {}
