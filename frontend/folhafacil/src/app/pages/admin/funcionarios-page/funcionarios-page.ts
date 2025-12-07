import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { StepperModule } from 'primeng/stepper';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { DatePicker } from 'primeng/datepicker';
import { Select } from 'primeng/select';
import { InputNumber } from 'primeng/inputnumber';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputMask } from 'primeng/inputmask';
import { FuncionarioService } from '../../../services/funcionario.service';
import { FuncionarioBeneficoDTO, FuncionarioDTO, FuncionarioFilterDTO, FuncionarioResponseDTO } from '../../../models/funcionario.model';
import { ActionsService } from '../../../services/actions.service';
import { BeneficioResponseDTO } from '../../../models/beneficio.model';
import { BeneficioService } from '../../../services/beneficio.service';
import { CarouselModule } from 'primeng/carousel';
import { SelectButton } from 'primeng/selectbutton';
import { MultiSelectModule } from 'primeng/multiselect';
import { MaskPipe } from '../../../shared/mask-pipe'

@Component({
  selector: 'app-funcionarios-page',
  standalone: true,
  imports: [
    CommonModule, 
    TableModule, 
    TagModule,
    ButtonModule,
    Dialog,
    StepperModule,
    InputTextModule,
    FloatLabel,
    DatePicker,
    Select,
    InputNumber,
    ReactiveFormsModule,
    FormsModule,
    InputMask,
    CarouselModule,
    SelectButton,
    MultiSelectModule,
    MaskPipe
  ],
  templateUrl: './funcionarios-page.html',
  styleUrl: './funcionarios-page.css'
})
export class FuncionariosPage {
  service = inject(FuncionarioService)
  actions = inject(ActionsService)
  cdr = inject(ChangeDetectorRef)
  beneficioService = inject(BeneficioService)

	funcionarioForm: FormGroup;
  beneficioForm: FormGroup;
  searchForm: FormGroup;

  isModal: boolean = false
  isEdit: boolean = false;
  steperValue: number = 1
  isModalBeneficios: boolean = false

  funcionarios!: FuncionarioResponseDTO[]
  beneficioObj: BeneficioResponseDTO[] = []
  funcionariobeneficios!: FuncionarioBeneficoDTO[]

  cargos: {name: string, value: string}[] = [
    {name : 'Estagiario', value: 'ESTAGIARIO'},
    {name : 'Funcionário', value: 'FUNCIONARIO'},
    {name : 'Admin', value: 'ADMIN'},
  ]

  status: {label: string, value: boolean}[] = [
    {label: "Ativo", value: true},
    {label: "Desativo", value: false}
  ]
    
  constructor(private fb: FormBuilder) {
    this.funcionarioForm = this.fb.group({
      id: [],
      nome: [],
      email: [],
      cpf: [],
      endereco: [],
      telefone: [],
      dataNascimento: [],
      cargo: [],
      dataAdmissao: [],
      salarioBase: [],
      horasDiarias: [],
      diasMensal: [],
      numDependentes: [],
      pensao: [],
      status: [],
      beneficios: this.fb.array([])
    });

    this.beneficioForm = this.fb.group({
      idBeneficio: [null],
      nomeBeneficio: [null],
      valor: [null]
    });

    this.searchForm = this.fb.group({
      nome: [],
      cpf: [],
      email: [],
      cargo: [],
      status : [],
      beneficios: [[]]
    })
	}

  ngOnInit(){
    this.buscarBeneficios()
    this.buscar()
  }

  novo(){
    this.isModal = true
  }

  closeModalBeneficios(){
    this.isModalBeneficios = false
    this.funcionariobeneficios = []
  }

  closeModal(){
    this.isModal = false
    this.isEdit = false
    this.steperValue = 1
    this.limparFuncionarioForm()
  }
  

  getFuncionarioForm(){
    const i : FuncionarioDTO = this.funcionarioForm.getRawValue();

    return i;
  }

  getSearchForm(){
    const f : FuncionarioFilterDTO = this.searchForm.value

    return f;
  }

  limparSearch(){
    this.searchForm = this.fb.group({
      nome: [],
      cpf: [],
      email: [],
      cargo: [],
      status : [],
      beneficios: [[]]
    })
  }

  removerBeneficio(id: any) {
    const index = this.beneficios.value.findIndex((b: any) => b.idBeneficio === id);
    if (index !== -1) {
      this.beneficios.removeAt(index);
    }
  }

  adicionarBeneficio() {
    const idBeneficio = this.beneficioForm.get('idBeneficio')?.value;
    const valor = this.beneficioForm.get('valor')?.value;

    if (!idBeneficio) return;

    const beneficioSelecionado = this.beneficioObj.find((b: any) => b.id === idBeneficio);
    const nomeBeneficio = beneficioSelecionado ? beneficioSelecionado.nome : 'Desconhecido';

    const index = this.beneficios.value.findIndex((b: any) => b.idBeneficio === idBeneficio);

    if (index !== -1) {
      this.beneficios.at(index).patchValue({ valor, nomeBeneficio });
    } else {
      this.beneficios.push(
        this.fb.group({
          idBeneficio,
          nomeBeneficio,
          valor
        })
      );
    }

    this.beneficioForm.reset();
  }

  buscar(){
    this.service.buscar(this.getSearchForm()).subscribe({
      next : (res: FuncionarioResponseDTO[]) =>{
        this.funcionarios = [...res]
        this.cdr.markForCheck();
      },
      error: () =>{

      }
    })
  }

  salvar(){
    this.service.salvar(this.getFuncionarioForm()).subscribe({
      next : (res: any) =>{
        this.actions.success('Funcionário salvo com sucesso')
        this.buscar()
        this.closeModal()
      },
      error : () => {

      }
    })
  }

  alterarStatus(uid: string, status: boolean) {
    this.service.alterarStatus(uid,status).subscribe({
      next : (res: any) =>{
        this.actions.info("Status alterado")
      }
    })
  }

  limparFuncionarioForm(){
    this.funcionarioForm = this.fb.group({
      id: [],
      nome: [],
      email: [],
      cpf: [],
      endereco: [],
      telefone: [],
      dataNascimento: [],
      cargo: [],
      dataAdmissao: [],
      salarioBase: [],
      horasDiarias: [],
      diasMensal: [],
      numDependentes: [],
      pensao: [],
      beneficios: this.fb.array([])
    });

    this.beneficioForm = this.fb.group({
      idBeneficio: [null],
      nomeBeneficio: [null],
      valor: [null]
    });
  }

  preencherFuncionarioForm(f: FuncionarioResponseDTO){
    this.service.buscarBeneficios(f.id).subscribe({
      next : (res: FuncionarioBeneficoDTO[]) =>{
        this.funcionarioForm = this.fb.group({
          id: [f.id],
          nome: [{value:f.nome, disabled: true}],
          email: [{value:f.email, disabled: true}],
          cpf: [f.cpf],
          endereco: [f.endereco],
          telefone: [f.telefone],
          dataNascimento: [new Date(f.dataNascimento)],
          cargo: [f.cargo],
          dataAdmissao: [new Date(f.dataAdmissao)],
          salarioBase: [f.salarioBase],
          horasDiarias: [f.horasDiarias],
          diasMensal: [f.diasMensal],
          numDependentes: [f.numDependentes],
          pensao: [f.pensao],
          status: [f.status],
          beneficios: this.fb.array(
          res.map(b => this.fb.group({
              idBeneficio: [b.idBeneficio],
              nomeBeneficio: [b.nomeBeneficio],
              valor: [b.valor]
            }))
          )
        });

        this.isEdit = true
        this.isModal = true
        this.cdr.markForCheck();
      }
    })
    
  }

  buscarBeneficios(){
    this.beneficioService.buscar().subscribe({
      next : (res: BeneficioResponseDTO[]) =>{
        this.beneficioObj = [...res]
          this.cdr.markForCheck();
      }
    })
  }

  buscarBeneficiosFuncionario(uid: string){
    this.service.buscarBeneficios(uid).subscribe({
      next : (res: FuncionarioBeneficoDTO[]) =>{
        if(res.length > 0){
          this.funcionariobeneficios = [...res]
          this.isModalBeneficios = true
          this.cdr.markForCheck();
        }else{
          this.actions.info("Sem benefícios")
        }
      }
    })
  }

  get beneficios(): FormArray {
    return this.funcionarioForm.get('beneficios') as FormArray;
  }

  getServerityCargo(c: any){
    switch (c) {
      case 'ADMIN':
        return 'danger';
      case 'ESTAGIARIO':
        return 'success';
      default:
        return 'info'
    }
  }
}
