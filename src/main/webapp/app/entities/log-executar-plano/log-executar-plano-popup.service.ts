import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LogExecutarPlano } from './log-executar-plano.model';
import { LogExecutarPlanoService } from './log-executar-plano.service';

@Injectable()
export class LogExecutarPlanoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private logExecutarPlanoService: LogExecutarPlanoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.logExecutarPlanoService.find(id).subscribe((logExecutarPlano) => {
                    if (logExecutarPlano.criado) {
                        logExecutarPlano.criado = {
                            year: logExecutarPlano.criado.getFullYear(),
                            month: logExecutarPlano.criado.getMonth() + 1,
                            day: logExecutarPlano.criado.getDate()
                        };
                    }
                    if (logExecutarPlano.finalizado) {
                        logExecutarPlano.finalizado = {
                            year: logExecutarPlano.finalizado.getFullYear(),
                            month: logExecutarPlano.finalizado.getMonth() + 1,
                            day: logExecutarPlano.finalizado.getDate()
                        };
                    }
                    this.ngbModalRef = this.logExecutarPlanoModalRef(component, logExecutarPlano);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.logExecutarPlanoModalRef(component, new LogExecutarPlano());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    logExecutarPlanoModalRef(component: Component, logExecutarPlano: LogExecutarPlano): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.logExecutarPlano = logExecutarPlano;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
