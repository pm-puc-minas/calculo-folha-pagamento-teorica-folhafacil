import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { LogFilterDTO, LogFolhaPagamentoResponseDTO, LogFuncionarioResponseDTO, LogSubFolhaPagamentoResponseDTO } from "../models/logs.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})
export class LogsService{
	http = inject(HttpClient);

    url = `${environment.API_URL}log/`

    buscarFuncionarios(f: LogFilterDTO): Observable<LogFuncionarioResponseDTO[]> {
        return this.http.post<LogFuncionarioResponseDTO[]>(`${this.url}funcionario`, {...f})
    }

    buscarFolhaPagamento(f: LogFilterDTO) : Observable<LogFolhaPagamentoResponseDTO[]> {
        return this.http.post<LogFolhaPagamentoResponseDTO[]>(`${this.url}folha-pagamento`, {...f})
    }

    buscarSubFolhaPagamento(id: Number) : Observable<LogSubFolhaPagamentoResponseDTO[]>{
        return this.http.get<LogSubFolhaPagamentoResponseDTO[]>(`${this.url}folha-pagamento/${id}/sub`)
    }
}