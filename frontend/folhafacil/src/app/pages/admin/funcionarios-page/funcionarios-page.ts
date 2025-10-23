import { Component } from '@angular/core';
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
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputMask } from 'primeng/inputmask';


@Component({
  selector: 'app-funcionarios-page',
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
    InputMask
  ],
  templateUrl: './funcionarios-page.html',
  styleUrl: './funcionarios-page.css'
})
export class FuncionariosPage {
	funcionarioForm: FormGroup;

  isModal: boolean = false
  isEdit: boolean = false;
  steperValue: number = 1

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
      valorPensao: [],
    });
	}

  showModal(){
    this.isModal = true
  }

  afterCloseModal(){
    this.steperValue = 1
  }

  closeModal(){
    this.isModal = false
    this.afterCloseModal()
  }

  getFuncionarioForm(){
    return this.funcionarioForm.value;
  }

  salvar(){
    console.log(this.getFuncionarioForm())
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
