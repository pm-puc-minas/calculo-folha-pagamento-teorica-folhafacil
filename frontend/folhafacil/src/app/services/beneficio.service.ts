import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { BeneficioDTO, BeneficioResponseDTO, BeneficioFuncionarioResponseDTO } from "../models/beneficio.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})

export class BeneficioService{
	http = inject(HttpClient);

    url = `${environment.API_URL}beneficio`

    salvar(i: BeneficioDTO){
        return this.http.post(`${this.url}`, {...i});
    }

    buscar(): Observable<BeneficioResponseDTO[]> {
        return this.http.get<BeneficioResponseDTO[]>(`${this.url}/buscar`)
    }

    buscarFuncionarios(id: number) : Observable<BeneficioFuncionarioResponseDTO[]> {
        return this.http.get<BeneficioFuncionarioResponseDTO[]>(`${this.url}/${id}/funcionarios`)
    }

    excluir(id: number){
        return this.http.delete(`${this.url}/${id}/excluir`)
    }
}