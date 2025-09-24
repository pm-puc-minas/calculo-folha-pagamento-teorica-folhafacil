import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";

@Injectable({
	providedIn: "root",
})
export class TesteService{
	http = inject(HttpClient);

    testePublico(){
        return this.http.get<any>(`${environment.API_URL}public`);
    }

    testeAdmin(){
        return this.http.get<any>(`${environment.API_URL}admin`);
    }
    
    testeUser(){
        return this.http.get<any>(`${environment.API_URL}user`);
    }
}