import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MiniMenuNavItem } from '../../models/router.model';
import { MiniMenuNav } from '../../components/mini-menu-nav/mini-menu-nav';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { KeyCloakService } from '../../services/keycloak.service';
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
  keyclaokService = inject(KeyCloakService);

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

      if(this.keyclaokService.hasRole("FF_FUNCIONARIOS_LISTAR")){
          route.routes.push({
            name: 'Funcionários',
            route: 'admin/funcionarios'
          })
      }

      if(this.keyclaokService.hasRole("FF_FOLHA_PAGAMENTO_LISTAR")){
        route.routes.push({
          name: 'Folha Pagamento',
          route: 'admin/folha-pagamento'
        })
      }

      if(this.keyclaokService.hasRole("FF_BENEFICIO_LISTAR")){
        route.routes.push({
          name: 'Benefícios',
          route: 'admin/beneficios'
        })
      }

      if(this.keyclaokService.hasRole("FF_HORAS_EXTRAS_LISTAR")){
        route.routes.push({
          name: 'Horas Extras',
          route: 'admin/horas-extras'
        })
      }

      if(this.keyclaokService.hasRole("FF_LOGS_LISTAR")){
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

    if(this.keyclaokService.hasRole("FF_MINHA_HORA_EXTRA_LISTAR")){
      route.routes.push({
        name: 'Minhas Horas Extras',
        route: 'funcionario/hora-extra'
      })
    }

    if(this.keyclaokService.hasRole("FF_FUNCIONARIO_MEUS_BENEFICIOS_LISTAR")){
      route.routes.push({
        name: 'Meus Benefícios',
        route: 'funcionario/meus-beneficios'
      })
    }

    if(this.keyclaokService.hasRole("FF_FOLHA_PAGAMENTO_MEUS_PAGAMENTOS")){
      route.routes.push({
        name: 'Meus Pagamentos',
        route: 'funcionario/meus-pagamentos'
      })
    }

    if(route.routes.length > 0){
      this.routes.push(route)
    }
  }

  logOut(){
    this.keyclaokService.logout()
  }

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }

  forgotPassword() {
    this.keyclaokService.redirectToForgotPassword();
  }

}
