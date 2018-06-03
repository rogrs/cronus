import { BaseEntity } from './../../shared';

export class Step implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public command?: string,
        public project?: BaseEntity,
    ) {
    }
}
