import { BaseEntity } from './../../shared';

export class Atividade implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public comando?: string,
        public pararNaFalha?: boolean,
        public plano?: BaseEntity,
    ) {
        this.pararNaFalha = false;
    }
}
