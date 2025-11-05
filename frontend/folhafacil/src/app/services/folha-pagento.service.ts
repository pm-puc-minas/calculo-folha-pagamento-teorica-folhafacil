import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";

@Injectable({
	providedIn: "root",
})

export class FolhaPagamentoService{
	http = inject(HttpClient);

    url = `${environment.API_URL}folha-pagamento`

    gerar(){
        return this.http.get(`${this.url}/gerar`)
    }

}