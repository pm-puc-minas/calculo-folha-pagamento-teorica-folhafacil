import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-funcionarios-page',
  imports: [
    CommonModule, 
    TableModule, 
    TagModule,
    ButtonModule
  ],
  templateUrl: './funcionarios-page.html',
  styleUrl: './funcionarios-page.css'
})
export class FuncionariosPage {
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
