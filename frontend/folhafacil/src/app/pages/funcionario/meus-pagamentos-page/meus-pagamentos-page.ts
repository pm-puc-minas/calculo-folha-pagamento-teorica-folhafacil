import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FolhaPagamentoService } from '../../../services/folha-pagamento.service';
import { FolhaPagamentoBeneficioResponseDTO, FolhaPagamentoHoraExtraResponseDTO, FolhaPagamentoResponseDTO } from '../../../models/folha-pagamento.model';
import { ActionsService } from '../../../services/actions.service';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';

@Component({
  selector: 'app-meus-pagamentos-page',
  imports: [
    CommonModule,
    TableModule,
    TagModule,
    Dialog,
    ButtonModule
  ],
  templateUrl: './meus-pagamentos-page.html',
  styleUrl: './meus-pagamentos-page.css'
})
export class MeusPagamentosPage {
  service = inject(FolhaPagamentoService)
  actions = inject(ActionsService)
  cdr = inject(ChangeDetectorRef)

  pagamentos: FolhaPagamentoResponseDTO[] = []
  beneficios: FolhaPagamentoBeneficioResponseDTO[] = []
  horasExtras: FolhaPagamentoHoraExtraResponseDTO[] = []
  
  isModalBeneficios : boolean = false
  isModalHorasExtras: boolean = false

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.service.meusPagamentos().subscribe({
      next: (res: FolhaPagamentoResponseDTO[]) => {
        if(res.length > 0){
          this.pagamentos = [...res]
          console.log(this.pagamentos)
        }else{
          this.actions.info("Sem pagamentos encontrados")
          this.cdr.markForCheck()
        }
      },
    })
  }

  buscarBeneficios(id: number){
    this.service.buscarBeneficios(id).subscribe({
      next : (res: FolhaPagamentoBeneficioResponseDTO[]) =>{
        if(res.length > 0){
          this.beneficios = [...res];
          this.cdr.markForCheck();
          this.isModalBeneficios = true
        }else{
          this.actions.info("Sem benefícios")
        }
      },
      error: () =>{
      }
    })
  }

  buscarHorasExtras(id: number){
    this.service.buscarHorasExtras(id).subscribe({
       next : (res: FolhaPagamentoHoraExtraResponseDTO[]) =>{
        if(res.length > 0){
          this.horasExtras = [...res];
          this.cdr.markForCheck();
          this.isModalHorasExtras = true
        }else{
          this.actions.info("Sem horas extras")
        }
      },
      error: () =>{
      }
    })
  }

  closeModalBeneficios(){
    this.isModalBeneficios = false
    this.beneficios = []
  }

  closeModalHorasExtras(){
    this.isModalHorasExtras = false
    this.horasExtras = []
  }

  getTotalHorasExtras(a: Date, b: Date){
    return this.getHoras(this.getDiffDias(a,b))
  }

  getValorHorasExtras(a: Date, b: Date, v: number){
    return `${(this.getDiffDias(a,b) * v).toFixed(2)}`
  }

  getDiffDias(a: Date, b: Date): number {
    const dataA = new Date(a);
    const dataB = new Date(b);

    const diffMs = dataA.getTime() - dataB.getTime();
    return diffMs / (1000 * 60 * 60);
  }


  getHoras(h: number){
    if(h == 0 || h == null) return "0h"
    return `${h.toFixed(0)}h ${((h%1) * 60).toFixed(0)}m`
  }

  getServityStatus(s: string){
    switch (s) {
      case 'PENDENTE':
        return 'warn';
      default:
        return 'success'
    }
  }

}
