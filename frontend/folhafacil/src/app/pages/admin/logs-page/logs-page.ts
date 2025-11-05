import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { TabsModule } from 'primeng/tabs';
import { LogsService } from '../../../services/logs.service';
import { LogFilterDTO, LogsFuncionarioResponseDTO } from '../../../models/logs.model';
import { TagModule } from 'primeng/tag';
import { AvatarModule } from 'primeng/avatar';
import { DatePicker } from 'primeng/datepicker';
import { FloatLabel } from 'primeng/floatlabel';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { isFormEmpty } from '../../../shared/utils/form'
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

  logsFuncionarios!: LogsFuncionarioResponseDTO[]

  ngOnInit(){
    this.buscarLogsFuncionarios()
  }

  filter(){
    this.buscarLogsFuncionarios()
  }

  buscarLogsFuncionarios(){
    this.service.buscarFuncionarios(this.getrSearchForm()).subscribe({
      next : (res: LogsFuncionarioResponseDTO[]) => {
        if(res.length == 0){
          this.actions.info("Nenhum registro encontrado")
        }else{
          this.logsFuncionarios = [...res];
          this.cdr.markForCheck();
        }
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

  getrSearchForm(){
    const f : LogFilterDTO = this.searchForm.value
    return f
  }

  getAvatar(n: string){
    const ns: string[] = n.split('.')
    return `${ns[0][0]}${ns[1][0]}`
  }

  getServerityTipo(c: any){
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

  get isFormEmpty(): boolean {
    return isFormEmpty(this.searchForm);
  }
}
