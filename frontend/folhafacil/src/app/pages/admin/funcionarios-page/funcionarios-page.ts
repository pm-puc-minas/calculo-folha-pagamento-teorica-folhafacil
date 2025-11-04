import { Component, inject } from '@angular/core';
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
import { FuncionarioDTO } from '../../../models/funcionario.model';
import { ActionsService } from '../../../services/actions.service';
import { BeneficioResponseDTO } from '../../../models/beneficio.model';
import { BeneficioService } from '../../../services/beneficio.service';
import { CarouselModule } from 'primeng/carousel';


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
    CarouselModule
  ],
  templateUrl: './funcionarios-page.html',
  styleUrl: './funcionarios-page.css'
})
export class FuncionariosPage {
  service = inject(FuncionarioService)
  actions = inject(ActionsService)
  beneficioService = inject(BeneficioService)

	funcionarioForm: FormGroup;
  beneficioForm: FormGroup;

  isModal: boolean = false
  isEdit: boolean = false;
  steperValue: number = 1

  beneficioObj: BeneficioResponseDTO[] = []

  cargos: {name: string, value: string}[] = [
    {name : 'Estagiario', value: 'ESTAGIARIO'},
    {name : 'Funcionário', value: 'FUNCIONARIO'},
    {name : 'Admin', value: 'ADMIN'},
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
      beneficios: this.fb.array([])
    });

    this.beneficioForm = this.fb.group({
      idBeneficio: [null],
      nomeBeneficio: [null],
      valor: [null]
    });

	}

  ngOnInit(){
    this.buscarBeneficios()
  }

  novo(){
    this.isModal = true
  }

  closeModal(){
    this.isModal = false
    this.isEdit = false
    this.steperValue = 1
    this.limparFuncionarioForm()
  }
  

  getFuncionarioForm(){
    const i : FuncionarioDTO = this.funcionarioForm.value;

    return i;
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


  salvar(){
    this.service.salvar(this.getFuncionarioForm()).subscribe({
      next : (res: any) =>{
        this.actions.success('Funcionário salvo com sucesso')
        this.closeModal()
      },
      error : () => {

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

  buscarBeneficios(){
    this.beneficioService.buscar().subscribe({
      next : (res: BeneficioResponseDTO[]) =>{
        this.beneficioObj = [...res]
      }
    })
  }

  dados = [
    { 
      nome: "Teste 1",
      telefone: "324654564556",
      endereco: "rua das ruas",
      cargo: "Junior",
      salario: 3000
    },
    { 
      nome: "Teste 2",
      telefone: "324345545645",
      endereco: "rua das vielas",
      cargo: "Pleno",
      salario: 6000
    },
    { 
      nome: "Teste 3",
      telefone: "31923647723",
      endereco: "rua dos becos",
      cargo: "Senior",
      salario: 6000
    },
    { 
      nome: "Teste 1",
      telefone: "324654564556",
      endereco: "rua das ruas",
      cargo: "Junior",
      salario: 3000
    },
    { 
      nome: "Teste 2",
      telefone: "324345545645",
      endereco: "rua das vielas",
      cargo: "Pleno",
      salario: 6000
    },
    { 
      nome: "Teste 3",
      telefone: "31923647723",
      endereco: "rua dos becos",
      cargo: "Senior",
      salario: 6000
    },
    { 
      nome: "Teste 1",
      telefone: "324654564556",
      endereco: "rua das ruas",
      cargo: "Junior",
      salario: 3000
    },
    { 
      nome: "Teste 2",
      telefone: "324345545645",
      endereco: "rua das vielas",
      cargo: "Pleno",
      salario: 6000
    },
    { 
      nome: "Teste 3",
      telefone: "31923647723",
      endereco: "rua dos becos",
      cargo: "Senior",
      salario: 6000
    },
    { 
      nome: "Teste 1",
      telefone: "324654564556",
      endereco: "rua das ruas",
      cargo: "Junior",
      salario: 3000
    },
    { 
      nome: "Teste 2",
      telefone: "324345545645",
      endereco: "rua das vielas",
      cargo: "Pleno",
      salario: 6000
    },
    { 
      nome: "Teste 3",
      telefone: "31923647723",
      endereco: "rua dos becos",
      cargo: "Senior",
      salario: 6000
    },
    { 
      nome: "Teste 1",
      telefone: "324654564556",
      endereco: "rua das ruas",
      cargo: "Junior",
      salario: 3000
    },
    { 
      nome: "Teste 2",
      telefone: "324345545645",
      endereco: "rua das vielas",
      cargo: "Pleno",
      salario: 6000
    },
    { 
      nome: "Teste 3",
      telefone: "31923647723",
      endereco: "rua dos becos",
      cargo: "Senior",
      salario: 6000
    },
    { 
      nome: "Teste 1",
      telefone: "324654564556",
      endereco: "rua das ruas",
      cargo: "Junior",
      salario: 3000
    },
    { 
      nome: "Teste 2",
      telefone: "324345545645",
      endereco: "rua das vielas",
      cargo: "Pleno",
      salario: 6000
    },
    { 
      nome: "Teste 3",
      telefone: "31923647723",
      endereco: "rua dos becos",
      cargo: "Senior",
      salario: 6000
    },
  ]

  get beneficios(): FormArray {
    return this.funcionarioForm.get('beneficios') as FormArray;
  }

  getServerityCargo(c: any){
    switch (c) {
            case 'Senior':
                return 'danger';
            case 'Pleno':
                return 'info';
            case 'Junior':
                return 'success';
            default:
              return 'info'
        }
  }
}
