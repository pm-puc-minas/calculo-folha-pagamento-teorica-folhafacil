import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { FloatLabel } from 'primeng/floatlabel';
import { AvatarModule } from 'primeng/avatar';
import { DatePicker } from 'primeng/datepicker';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActionsService } from '../../../services/actions.service';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { Select } from 'primeng/select';
import { HoraExtraService } from '../../../services/hora-extra.service';
import { HoraExtraFilterDTO, HoraExtraResponseDTO } from '../../../models/horaextra.model';
import { FuncionarioService } from '../../../services/funcionario.service';
import { FuncionarioFilterDTO, FuncionarioResponseDTO } from '../../../models/funcionario.model';

@Component({
  selector: 'app-horas-extras-page',
  imports: [
    CommonModule,
    TableModule,
    TagModule,
    ButtonModule,
    FloatLabel,
    AvatarModule,
    DatePicker,
    FormsModule,
    ReactiveFormsModule,
    ConfirmPopupModule,
    Select
  ],
  templateUrl: './horas-extras-page.html',
  styleUrl: './horas-extras-page.css',
  providers: [ConfirmationService]
})
export class HorasExtrasPage {
  service = inject(HoraExtraService)
  actions = inject(ActionsService)
  funcionarioService = inject(FuncionarioService)
  cdr = inject(ChangeDetectorRef)
  confirmationService = inject(ConfirmationService)

  horaExtras!: HoraExtraResponseDTO[]
  funcionarios: FuncionarioResponseDTO[] = []

  searchForm: FormGroup

  constructor(private fb: FormBuilder){
    this.searchForm = this.fb.group({
      idFuncionario: [],
      dataInicio: [],
      dataFim: []
    })
  }

  ngOnInit(){
    this.buscar()
    this.buscarFuncionarios()
  }

  buscar(){
    this.service.buscar(this.getSearchForm()).subscribe({
      next: (res: HoraExtraResponseDTO[]) => {
        if(res.length > 0){
          this.horaExtras = [...res]
          this.cdr.markForCheck()
        }else{
          this.actions.info('Nenhuma hora extra encontrada')
        }
      },
    })
  }

  buscarFuncionarios(){
    const f: FuncionarioFilterDTO = {
      nome: null,
      cpf: null,
      email: null,
      cargo: null,
      status : null,
      beneficios: []
    }

    this.funcionarioService.buscar(f).subscribe({
      next : (res: FuncionarioResponseDTO[]) =>{
        this.funcionarios = [...res]
        this.cdr.markForCheck()
      }
    })
  }

  cancelar(event: Event, id: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Deseja cancelar?',
      accept: () => {
        this.service.cancelar(id).subscribe({
          next : () =>{
            this.actions.info("Hora Extra cancelada com sucesso")
            this.buscar()
          }
        })
      },
      reject: () => {
      }
    });
  }

  limparSearch(){
    this.searchForm = this.fb.group({
      idFuncionario: [],
      dataInicio: [],
      dataFim: []
    })
  }

  getSearchForm(){
    const f: HoraExtraFilterDTO = this.searchForm.getRawValue();

    return f;
  }

  getAvatar(n: string){
    const ns: string[] = n.split('.')
    return `${ns[0][0]}${ns[1][0]}`
  }

  getServerityStatus(s: string) {
    switch (s) {
      case 'EM_ANDAMENTO':
        return 'info';
      case 'CONCLUIDA':
        return 'success';
      default:
        return 'danger'
    }
  }
}
