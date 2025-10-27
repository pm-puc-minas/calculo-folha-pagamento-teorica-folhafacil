import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { FuncionarioDTO } from "../models/funcionario.model";
import { LogsFuncionarioResponseDTO } from "../models/logs.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})
export class LogsService{
	http = inject(HttpClient);

    url = `${environment.API_URL}log/`

    buscarFuncionarios(): Observable<LogsFuncionarioResponseDTO[]> {
        return this.http.get<LogsFuncionarioResponseDTO[]>(`${this.url}funcionario`)
    }
}