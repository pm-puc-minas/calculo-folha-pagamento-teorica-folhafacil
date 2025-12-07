import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { FolhaPagamentoBeneficioResponseDTO, FolhaPagamentoFilterDTO, FolhaPagamentoHoraExtraResponseDTO, FolhaPagamentoResponseDTO } from "../models/folha-pagamento.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})

export class FolhaPagamentoService{
	http = inject(HttpClient);

    url = `${environment.API_URL}folha-pagamento`

    gerar(){
        return this.http.get(`${this.url}/gerar`)
    }

    pagar(ids: number[]){
        return this.http.post(`${this.url}/pagar`, ids)
    }

    buscar(f: FolhaPagamentoFilterDTO) : Observable<FolhaPagamentoResponseDTO[]>{
        return this.http.post<FolhaPagamentoResponseDTO[]>(`${this.url}/buscar`, {... f})
    }

    buscarBeneficios(id: number) : Observable<FolhaPagamentoBeneficioResponseDTO[]>{
        return this.http.get<FolhaPagamentoBeneficioResponseDTO[]>(`${this.url}/${id}/beneficios`)
    }

    buscarHorasExtras(id: number) : Observable<FolhaPagamentoHoraExtraResponseDTO[]>{
        return this.http.get<FolhaPagamentoHoraExtraResponseDTO[]>(`${this.url}/${id}/horas-extras`)
    }

    meusPagamentos() : Observable<FolhaPagamentoResponseDTO[]>{
        return this.http.get<FolhaPagamentoResponseDTO[]>(`${this.url}/meus-pagamentos`)
    }
}