import { BaseEntity } from './../../shared';

export const enum TipoPlano {
    'UNITARIO',
    'SEGURANCA',
    'SISTEMA',
    'AMBIENTE',
    'INTEGRACAO',
    'DESEMPENHO'
}

export class Plano implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public detalhes?: string,
        public tipo?: TipoPlano,
        public atividades?: BaseEntity[],
        public executarPlanos?: BaseEntity[],
        public projetos?: BaseEntity,
        public scripts?: BaseEntity,
    ) {
    }
}
