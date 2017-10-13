import { BaseEntity } from './../../shared';

export class ExecutarPlano implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public detalhes?: string,
        public pararNaFalha?: boolean,
        public logExecutarPlanos?: BaseEntity[],
        public plano?: BaseEntity,
    ) {
        this.pararNaFalha = false;
    }
}
