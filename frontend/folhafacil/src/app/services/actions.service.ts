import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ActionsService {
    constructor(private messageService: MessageService) {}

    private requestsCount = 0;
    private _loading = new BehaviorSubject<boolean>(false);
    loading$ = this._loading.asObservable();

    showLoad() {
        this.requestsCount++;
        if (this.requestsCount === 1) {
        this._loading.next(true);
        }
    }

    hideLoad() {
        if (this.requestsCount > 0) {
        this.requestsCount--;
        }
        if (this.requestsCount === 0) {
        this._loading.next(false);
        }
    }

    success(msg: string, summary: string = 'Sucesso') {
        this.messageService.add({ key: 'global', severity: 'success', summary, detail: msg });
    }

    info(msg: string, summary: string = 'Info') {
        this.messageService.add({ key: 'global', severity: 'info', summary, detail: msg });
    }

    warn(msg: string, summary: string = 'Atenção') {
        this.messageService.add({ key: 'global', severity: 'warn', summary, detail: msg });
    }

    danger(msg: string, summary: string = 'Erro') {
        this.messageService.add({ key: 'global', severity: 'error', summary, detail: msg });
    }
}
