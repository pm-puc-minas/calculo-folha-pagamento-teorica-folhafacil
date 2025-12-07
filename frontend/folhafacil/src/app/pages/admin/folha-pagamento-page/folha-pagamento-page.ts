import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { FloatLabel } from 'primeng/floatlabel';
import { AvatarModule } from 'primeng/avatar';
import { DatePicker } from 'primeng/datepicker';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActionsService } from '../../../services/actions.service';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { FolhaPagamentoService } from '../../../services/folha-pagamento.service';
import { FolhaPagamentoBeneficioResponseDTO, FolhaPagamentoFilterDTO, FolhaPagamentoHoraExtraResponseDTO, FolhaPagamentoResponseDTO } from '../../../models/folha-pagamento.model';
import { FuncionarioService } from '../../../services/funcionario.service';
import { MultiSelectModule } from 'primeng/multiselect';
import { FuncionarioFilterDTO, FuncionarioResponseDTO } from '../../../models/funcionario.model';
import { Select } from 'primeng/select';

@Component({
  selector: 'app-folha-pagamento-page',
  imports: [
    CommonModule,
    TableModule,
    TagModule,
    ButtonModule,
    Dialog,
    AvatarModule,
    FloatLabel,
    DatePicker,
    FormsModule,
    ReactiveFormsModule,
    ConfirmPopupModule,
    MultiSelectModule,
    Select
  ],
  templateUrl: './folha-pagamento-page.html',
  styleUrl: './folha-pagamento-page.css',
  providers: [ConfirmationService]
})
export class FolhaPagamentoPage {
  service = inject(FolhaPagamentoService)
  funcionarisoService = inject(FuncionarioService)
  confirmationService = inject(ConfirmationService)
  actions = inject(ActionsService)
  cdr = inject(ChangeDetectorRef)

  searchForm: FormGroup;

  folhaDePagamentos!: FolhaPagamentoResponseDTO[]
  beneficios!: FolhaPagamentoBeneficioResponseDTO[]
  horasExtras!: FolhaPagamentoHoraExtraResponseDTO[]
  selectedItens!: FolhaPagamentoResponseDTO[]
  funcionarios: FuncionarioResponseDTO[] = []

  isModalBeneficios : boolean = false
  isModalHorasExtras: boolean = false

  hoje : Date = new Date();

  cargos: {name: string, value: string}[] = [
    {name : 'Estagiario', value: 'ESTAGIARIO'},
    {name : 'Funcionário', value: 'FUNCIONARIO'},
    {name : 'Admin', value: 'ADMIN'},
  ]

  status: {label: string, value: string}[] = [
    {label: "PENDENTE", value: "PENDENTE"},
    {label: "PAGO", value: "PAGO"}
  ]

  constructor(private fb: FormBuilder) {
    this.searchForm = this.fb.group({
      ids: [[]],
      data: [new Date(this.hoje.getFullYear(), this.hoje.getMonth(), 1)],
      funcionarios: [[]],
      status: [],
      tipoFuncionario: []
    });
	}

  ngOnInit(){
    this.buscarFuncionarios()
  }

  selectIds(){
    return this.selectedItens.map(f => f.id)
  }

  buscar(){
    this.service.buscar(this.getSearchForm()).subscribe({
      next: (res: FolhaPagamentoResponseDTO[]) =>{
        if(res.length == 0){
          this.actions.info("Nenhum registro encontrado!")
        }else{
          this.folhaDePagamentos = [...res];
          this.cdr.markForCheck();
        }
      },
      error: () =>{
      }
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

  buscarFuncionarios(){
    const f : FuncionarioFilterDTO = {
      nome: null,
      cpf: null,
      email: null,
      cargo: null,
      status : null,
      beneficios: []
    }
    this.funcionarisoService.buscar(f).subscribe({
      next : (res: FuncionarioResponseDTO[]) =>{
        this.funcionarios = [...res]
        this.cdr.markForCheck();
        console.log(res)
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

  gerar(event: Event) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Deseja gerar/atualizar? Essa ações pode demorar! (Referente ao mês atual)',
      accept: () => {
        this.service.gerar().subscribe({
          next: () =>{
            this.actions.success("Folha gerada/atualizada com sucesso")
          },
          error: () =>{

          }
        })
      },
      reject: () => {
      }
    });
  }

  pagar(event: Event){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Deseja pagar as folhas selecionadas?',
      accept: () => {
        this.service.pagar(this.selectIds()).subscribe({
          next: () =>{
            this.actions.info("Folhas pagas")
            this.buscar()
          }
        })
      },
      reject: () => {
        console.log('sai')
      }
    });
  }

  getServityStatus(s: string){
    switch (s) {
      case 'PENDENTE':
        return 'warn';
      default:
        return 'success'
    }
  }

  limparSearch(){
    this.searchForm = this.fb.group({
      ids: [[]],
      data: [],
      funcionarios: [[]],
      status: [],
      tipoFuncionario: []
    });
  }

  getSearchForm(){
    const f : FolhaPagamentoFilterDTO = this.searchForm.value

    return f;
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

  getAvatar(n: string){
    const ns: string[] = n.split('.')
    return `${ns[0][0]}${ns[1][0]}`
  }
}
