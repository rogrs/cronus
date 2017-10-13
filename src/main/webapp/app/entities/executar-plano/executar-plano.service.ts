import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ExecutarPlano } from './executar-plano.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ExecutarPlanoService {

    private resourceUrl = SERVER_API_URL + 'api/executar-planos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/executar-planos';

    constructor(private http: Http) { }

    create(executarPlano: ExecutarPlano): Observable<ExecutarPlano> {
        const copy = this.convert(executarPlano);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(executarPlano: ExecutarPlano): Observable<ExecutarPlano> {
        const copy = this.convert(executarPlano);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ExecutarPlano> {
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
     * Convert a returned JSON object to ExecutarPlano.
     */
    private convertItemFromServer(json: any): ExecutarPlano {
        const entity: ExecutarPlano = Object.assign(new ExecutarPlano(), json);
        return entity;
    }

    /**
     * Convert a ExecutarPlano to a JSON which can be sent to the server.
     */
    private convert(executarPlano: ExecutarPlano): ExecutarPlano {
        const copy: ExecutarPlano = Object.assign({}, executarPlano);
        return copy;
    }
}
