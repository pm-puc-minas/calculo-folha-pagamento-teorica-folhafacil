import { Component, inject } from '@angular/core';
import { TesteService } from '../../../services/teste.service';
import { error } from 'console';

@Component({
  selector: 'app-hora-extra-page',
  imports: [],
  templateUrl: './hora-extra-page.html',
  styleUrl: './hora-extra-page.css'
})
export class HoraExtraPage {
  serivce = inject(TesteService);

  admin(){
    this.serivce.testeAdmin().subscribe(() =>{
      next: (res: any) => {
        console.log(res)
      }
      error: () =>{
        console.error("erro1")
      }
    })
  }

  user(){
    this.serivce.testeUser().subscribe(() =>{
      next: (res: any) => {
        console.log(res)
      }
      error: () =>{
        console.error("erro2")
      }
    })
  }

  public(){
    this.serivce.testePublico().subscribe(() =>{
      next: (res: any) => {
        console.log(res)
      }
      error: () =>{
        console.error("erro3")
      }
    })
  }
}
