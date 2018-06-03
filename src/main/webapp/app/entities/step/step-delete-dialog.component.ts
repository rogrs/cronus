import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Step } from './step.model';
import { StepPopupService } from './step-popup.service';
import { StepService } from './step.service';

@Component({
    selector: 'jhi-step-delete-dialog',
    templateUrl: './step-delete-dialog.component.html'
})
export class StepDeleteDialogComponent {

    step: Step;

    constructor(
        private stepService: StepService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stepService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stepListModification',
                content: 'Deleted an step'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-step-delete-popup',
    template: ''
})
export class StepDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stepPopupService: StepPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.stepPopupService
                .open(StepDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
