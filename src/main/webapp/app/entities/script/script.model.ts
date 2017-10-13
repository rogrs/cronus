import { BaseEntity } from './../../shared';

export class Script implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public path?: string,
        public planos?: BaseEntity[],
        public plugin?: BaseEntity,
    ) {
    }
}
