import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { LogFilterDTO, LogsFuncionarioResponseDTO } from "../models/logs.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})
export class LogsService{
	http = inject(HttpClient);

    url = `${environment.API_URL}log/`

    buscarFuncionarios(f: LogFilterDTO): Observable<LogsFuncionarioResponseDTO[]> {
        return this.http.post<LogsFuncionarioResponseDTO[]>(`${this.url}funcionario`, {...f})
    }
}