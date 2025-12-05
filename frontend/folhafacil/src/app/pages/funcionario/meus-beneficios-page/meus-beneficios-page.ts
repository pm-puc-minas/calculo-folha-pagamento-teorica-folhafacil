import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { FuncionarioService } from '../../../services/funcionario.service';
import { FuncionarioBeneficoDTO } from '../../../models/funcionario.model';

@Component({
  selector: 'app-meus-beneficios-page',
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './meus-beneficios-page.html',
  styleUrl: './meus-beneficios-page.css'
})
export class MeusBeneficiosPage {
  service = inject(FuncionarioService);
  cdr = inject(ChangeDetectorRef)


  ngOnInit(){
    this.buscar()
  }

  beneficios!: FuncionarioBeneficoDTO[]

  buscar(){
    this.service.buscarMeusBeneficios().subscribe({
      next: (res: FuncionarioBeneficoDTO[]) => {
        this.beneficios = [...res]
        this.cdr.markForCheck()
      },
    })
  }
}
