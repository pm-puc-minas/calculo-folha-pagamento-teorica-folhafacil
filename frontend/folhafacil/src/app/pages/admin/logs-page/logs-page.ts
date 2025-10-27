import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { TabsModule } from 'primeng/tabs';
import { LogsService } from '../../../services/logs.service';
import { LogsFuncionarioResponseDTO } from '../../../models/logs.model';
import { TagModule } from 'primeng/tag';
import { AvatarModule } from 'primeng/avatar';


@Component({
  selector: 'app-logs-page',
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    Dialog,
    TabsModule,
    TagModule,
    AvatarModule
  ],
  templateUrl: './logs-page.html',
  styleUrl: './logs-page.css'
})
export class LogsPage implements OnInit{
  service = inject(LogsService)
  cdr = inject(ChangeDetectorRef)

  logsFuncionarios!: LogsFuncionarioResponseDTO[]

  ngOnInit(){
    this.buscarLogsFuncionarios()
  }

  buscarLogsFuncionarios(){
    this.service.buscarFuncionarios().subscribe({
      next : (res: LogsFuncionarioResponseDTO[]) => {
        this.logsFuncionarios = res;
        this.cdr.detectChanges();
      },
      error : () =>{

      }
    })
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
}
