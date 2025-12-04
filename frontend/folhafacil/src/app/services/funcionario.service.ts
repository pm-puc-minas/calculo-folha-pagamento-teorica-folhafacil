import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { FuncionarioBeneficoDTO, FuncionarioDTO, FuncionarioFilterDTO, FuncionarioResponseDTO } from "../models/funcionario.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})

export class FuncionarioService{
	http = inject(HttpClient);

    url = `${environment.API_URL}funcionario`

    salvar(i: FuncionarioDTO){
        return this.http.post(`${this.url}`, {...i});
    }

    buscar(f: FuncionarioFilterDTO) : Observable<FuncionarioResponseDTO[]>{
        return this.http.post<FuncionarioResponseDTO[]>(`${this.url}/buscar`, {...f});
    }

    buscarBeneficios(uid: string) : Observable<FuncionarioBeneficoDTO[]> {
        return this.http.get<FuncionarioBeneficoDTO[]>(`${this.url}/${uid}/beneficios`)
    }

    alterarStatus(uid: string, status: boolean){
        if(status){
            return this.http.get(`${this.url}/${uid}/habilitar`)
        }
        return this.http.get(`${this.url}/${uid}/desabilitar`)
    }
}