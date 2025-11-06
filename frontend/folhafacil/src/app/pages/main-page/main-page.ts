import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MiniMenuNavItem } from '../../models/router.model';
import { MiniMenuNav } from '../../components/mini-menu-nav/mini-menu-nav';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { KeyCloackService } from '../../services/keycloack.service';
import { ActionsService } from '../../services/actions.service';
import { Toast } from 'primeng/toast';


@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    CommonModule,
    MatSidenavModule, 
    MatIconModule,
    MiniMenuNav,
    RouterLink, 
    RouterLinkActive, 
    RouterOutlet,
    Toast
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css'
})
export class MainPage {
  keyclaockService = inject(KeyCloackService);

  isExpanded = true;

  isLoading = false;

  routes: MiniMenuNavItem[] = []

  constructor(private actionsService: ActionsService) {
    this.actionsService.loading$.subscribe(value => {
      this.isLoading = value;
    });
  }

  ngOnInit(){
    this.hasAdmin()
    this.hasFuncionario()
  }

  hasAdmin(){
      const route : MiniMenuNavItem = {
        title: "Admin",
        routes: []
      }

      if(this.keyclaockService.hasRole("FF_FUNCIONARIOS_LISTAR")){
          route.routes.push({
            name: 'Funcionários',
            route: 'admin/funcionarios'
          })
      }

      if(this.keyclaockService.hasRole("FF_FOLHA_PAGAMENTO_LISTAR")){
        route.routes.push({
          name: 'Folha Pagamento',
          route: 'admin/folha-pagamento'
        })
      }

      if(this.keyclaockService.hasRole("FF_LOGS_LISTAR")){
        route.routes.push({
          name: 'Logs',
          route: 'admin/logs'
        })
      }

      if(this.keyclaockService.hasRole("FF_BENEFICIO_LISTAR")){
        route.routes.push({
          name: 'Benefícios',
          route: 'admin/beneficios'
        })
      }

      if(route.routes.length > 0){
        this.routes.push(route)
      }
  }

  hasFuncionario(){
    const route : MiniMenuNavItem = {
      title: "Funcionário",
      routes: []
    }

    if(this.keyclaockService.hasRole("FF_MINHA_HORA_EXTRA_LISTAR")){
      route.routes.push({
        name: 'Hora Extra',
        route: 'funcionario/hora-extra'
      })
    }

    if(route.routes.length > 0){
      this.routes.push(route)
    }
  }

  logOut(){
    this.keyclaockService.logout()
  }

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }
}
