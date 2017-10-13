import { BaseEntity } from './../../shared';

export class Projeto implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public detalhes?: string,
        public planos?: BaseEntity[],
    ) {
    }
}
