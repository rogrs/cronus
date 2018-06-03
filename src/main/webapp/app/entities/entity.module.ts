import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CronusProjectModule } from './project/project.module';
import { CronusStepModule } from './step/step.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CronusProjectModule,
        CronusStepModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CronusEntityModule {}
