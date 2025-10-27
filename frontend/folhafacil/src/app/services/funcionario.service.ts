import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { FuncionarioDTO } from "../models/funcionario.model";

@Injectable({
	providedIn: "root",
})
export class FuncionarioService{
	http = inject(HttpClient);

    url = `${environment.API_URL}funcionario`

    salvar(i: FuncionarioDTO){
        return this.http.post(`${this.url}`, {...i});
    }
}