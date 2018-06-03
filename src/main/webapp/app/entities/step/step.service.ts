import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Step } from './step.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Step>;

@Injectable()
export class StepService {

    private resourceUrl =  SERVER_API_URL + 'api/steps';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/steps';

    constructor(private http: HttpClient) { }

    create(step: Step): Observable<EntityResponseType> {
        const copy = this.convert(step);
        return this.http.post<Step>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(step: Step): Observable<EntityResponseType> {
        const copy = this.convert(step);
        return this.http.put<Step>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Step>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Step[]>> {
        const options = createRequestOption(req);
        return this.http.get<Step[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Step[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Step[]>> {
        const options = createRequestOption(req);
        return this.http.get<Step[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Step[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Step = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Step[]>): HttpResponse<Step[]> {
        const jsonResponse: Step[] = res.body;
        const body: Step[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Step.
     */
    private convertItemFromServer(step: Step): Step {
        const copy: Step = Object.assign({}, step);
        return copy;
    }

    /**
     * Convert a Step to a JSON which can be sent to the server.
     */
    private convert(step: Step): Step {
        const copy: Step = Object.assign({}, step);
        return copy;
    }
}
