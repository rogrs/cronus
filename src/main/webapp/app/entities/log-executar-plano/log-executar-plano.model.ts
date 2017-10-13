import { BaseEntity } from './../../shared';

export const enum Status {
    'SUCESSO',
    'FALHA',
    'FALHA_TEMPORARIA'
}

export class LogExecutarPlano implements BaseEntity {
    constructor(
        public id?: number,
        public criado?: any,
        public finalizado?: any,
        public mensagem?: string,
        public status?: Status,
        public execucao?: BaseEntity,
    ) {
    }
}
