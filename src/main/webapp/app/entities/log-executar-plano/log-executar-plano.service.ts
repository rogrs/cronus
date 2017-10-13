import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LogExecutarPlano } from './log-executar-plano.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LogExecutarPlanoService {

    private resourceUrl = SERVER_API_URL + 'api/log-executar-planos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/log-executar-planos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(logExecutarPlano: LogExecutarPlano): Observable<LogExecutarPlano> {
        const copy = this.convert(logExecutarPlano);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(logExecutarPlano: LogExecutarPlano): Observable<LogExecutarPlano> {
        const copy = this.convert(logExecutarPlano);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LogExecutarPlano> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to LogExecutarPlano.
     */
    private convertItemFromServer(json: any): LogExecutarPlano {
        const entity: LogExecutarPlano = Object.assign(new LogExecutarPlano(), json);
        entity.criado = this.dateUtils
            .convertLocalDateFromServer(json.criado);
        entity.finalizado = this.dateUtils
            .convertLocalDateFromServer(json.finalizado);
        return entity;
    }

    /**
     * Convert a LogExecutarPlano to a JSON which can be sent to the server.
     */
    private convert(logExecutarPlano: LogExecutarPlano): LogExecutarPlano {
        const copy: LogExecutarPlano = Object.assign({}, logExecutarPlano);
        copy.criado = this.dateUtils
            .convertLocalDateToServer(logExecutarPlano.criado);
        copy.finalizado = this.dateUtils
            .convertLocalDateToServer(logExecutarPlano.finalizado);
        return copy;
    }
}
