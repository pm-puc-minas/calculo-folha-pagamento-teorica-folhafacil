import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { HoraExtraDTO, HoraExtraResponseDTO } from "../models/horaextra.model";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})

export class HoraExtraService{
	http = inject(HttpClient);

    url = `${environment.API_URL}hora-extra`

    inicar(i : HoraExtraDTO){
        return this.http.post(`${this.url}/iniciar`, {...i});
    }

    minhasHoras() : Observable<HoraExtraResponseDTO[]>{
        return this.http.get<HoraExtraResponseDTO[]>(`${this.url}/minhas`)
    }

    finalizar(id: number){
        return this.http.get(`${this.url}/${id}/finalizar`)
    }
}