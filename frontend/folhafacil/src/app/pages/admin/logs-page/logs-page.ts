import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { TabsModule } from 'primeng/tabs';
import { LogsService } from '../../../services/logs.service';
import { LogFilterDTO, LogFolhaPagamentoResponseDTO, LogFuncionarioResponseDTO, LogSubFolhaPagamentoResponseDTO } from '../../../models/logs.model';
import { TagModule } from 'primeng/tag';
import { AvatarModule } from 'primeng/avatar';
import { DatePicker } from 'primeng/datepicker';
import { FloatLabel } from 'primeng/floatlabel';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActionsService } from '../../../services/actions.service';

@Component({
  selector: 'app-logs-page',
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    Dialog,
    TabsModule,
    TagModule,
    AvatarModule,
    DatePicker,
    FloatLabel,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './logs-page.html',
  styleUrl: './logs-page.css'
})
export class LogsPage{
  service = inject(LogsService)
  actions = inject(ActionsService)
  cdr = inject(ChangeDetectorRef)

	searchForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.searchForm = this.fb.group({
      dataInicio: [],
      dataFim: [],
    });
	}

  logsFuncionarios!: LogFuncionarioResponseDTO[]
  logsFolhaPagamentos!: LogFolhaPagamentoResponseDTO[]
  logsSubFolhaPagamentos!: LogSubFolhaPagamentoResponseDTO[]

  isModal : boolean = false;

  ngOnInit(){
    this.filter()
  }

  filter(){
    this.buscarLogsFuncionarios()
    this.buscarLogsFolhaPagamento()
  }

  closeModal(){
    this.logsSubFolhaPagamentos = []
  }

  buscarLogsFuncionarios(){
    this.service.buscarFuncionarios(this.getSearchForm()).subscribe({
      next : (res: LogFuncionarioResponseDTO[]) => {
        if(res.length == 0){
          this.actions.info("Nenhum log de Funcionario encontrado")
        }else{
          this.logsFuncionarios = [...res];
          this.cdr.markForCheck();
        }
      },
      error : () =>{
      }
    })
  }

  buscarLogsFolhaPagamento(){
    this.service.buscarFolhaPagamento(this.getSearchForm()).subscribe({
      next : (res: LogFolhaPagamentoResponseDTO[]) => {
        if(res.length == 0){
          this.actions.info("Nenhum log de folha encontrado")
        }else{
          this.logsFolhaPagamentos = [...res];
          this.cdr.markForCheck();
        }
      },
      error : () =>{
      }
    })
  }

  buscarSubLog(id: number){
    this.service.buscarSubFolhaPagamento(id).subscribe({
      next : (res: LogSubFolhaPagamentoResponseDTO[]) => {
        this.logsSubFolhaPagamentos = [...res];
        this.cdr.markForCheck();
        this.isModal = true
      },
      error : () =>{
      }
    })
  }

  limparSearch(){
    this.searchForm = this.fb.group({
      dataInicio: [],
      dataFim: [],
    });
  }

  getSearchForm(){
    const f : LogFilterDTO = this.searchForm.value
    return f
  }

  getAvatar(n: string){
    const ns: string[] = n.split('.')
    return `${ns[0][0]}${ns[1][0]}`
  }

  getServerityTipoFuncionario(c: string){
    switch (c) {
      case 'CRIADO':
        return 'success';
      case 'ALTERADO':
        return 'info';
      case 'DESABILITADO':
        return 'danger';
      default:
        return 'warn'
    }
  }

  getServerityTipoFolhaPagamento(c: string){
    switch (c) {
      case 'PAGAMENTO':
        return 'success';
      case 'GERADA_ATUALIZADA':
        return 'info';
      default:
        return 'warn'
    }
  }
  getServerityTipoSubFolhaPagamento(c: string){
    switch (c) {
      case 'GERADO':
        return 'info';
      case 'ATUALIZADO':
        return 'warn';
      case 'PAGO':
        return 'success';
      default:
        return 'danger'
    }
  }

  getHoras(h: number){
    if(h == 0 || h == null) return "0h"
    return `${h.toFixed(0)}h ${((h%1) * 60).toFixed(0)}m`
  }
}
