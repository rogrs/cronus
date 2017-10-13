import { BaseEntity } from './../../shared';

export class Plugin implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public comando?: string,
        public scripts?: BaseEntity[],
    ) {
    }
}
