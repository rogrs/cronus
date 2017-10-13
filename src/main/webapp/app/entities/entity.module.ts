import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutobotAtividadeModule } from './atividade/atividade.module';
import { AutobotPluginModule } from './plugin/plugin.module';
import { AutobotProjetoModule } from './projeto/projeto.module';
import { AutobotPlanoModule } from './plano/plano.module';
import { AutobotScriptModule } from './script/script.module';
import { AutobotExecutarPlanoModule } from './executar-plano/executar-plano.module';
import { AutobotLogExecutarPlanoModule } from './log-executar-plano/log-executar-plano.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AutobotAtividadeModule,
        AutobotPluginModule,
        AutobotProjetoModule,
        AutobotPlanoModule,
        AutobotScriptModule,
        AutobotExecutarPlanoModule,
        AutobotLogExecutarPlanoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutobotEntityModule {}
