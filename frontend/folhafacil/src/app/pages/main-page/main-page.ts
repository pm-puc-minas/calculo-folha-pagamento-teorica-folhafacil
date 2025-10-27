import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MiniMenuNavItem } from '../../models/router.model';
import { MiniMenuNav } from '../../components/mini-menu-nav/mini-menu-nav';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { KeyCloackService } from '../../services/keycloack.service';


@Component({
  selector: 'app-main-page',
  imports: [
    CommonModule,
    MatSidenavModule, 
    MatIconModule,
    MiniMenuNav,
    RouterLink, 
    RouterLinkActive, 
    RouterOutlet
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css'
})
export class MainPage {
  keyclaockService = inject(KeyCloackService);

  isExpanded = true;

  routes: MiniMenuNavItem[] = [
    
  ]

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

      if(this.keyclaockService.hasRole("FF_FOLHA_SALARIAL_LISTAR")){
        route.routes.push({
          name: 'Folha Salarial',
          route: 'admin/folha-salarial'
        })
      }

      if(this.keyclaockService.hasRole("FF_LOGS_LISTAR")){
        route.routes.push({
          name: 'Logs',
          route: 'admin/logs'
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

    if(this.keyclaockService.hasRole("FF_HORA_EXTRAS_LISTAR")){
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
