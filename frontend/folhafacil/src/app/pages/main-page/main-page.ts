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
    this.hasUser()
  }

  hasAdmin(){
    if(this.keyclaockService.hasRole('ADMIN')){
      const route : MiniMenuNavItem = {
        title: "Admin",
          routes: [
            {
              name: 'Funcionarios',
              route: 'admin/funcionarios'
            },
            {
              name: 'Folha Salarial',
              route: 'admin/folha-salarial'
            }
          ]
      }
      this.routes.push(route)
    }
  }

  hasUser(){
    const route : MiniMenuNavItem = {
      title: "Funcionario",
      routes: [
        {
          name: 'Hora Extra',
          route: 'funcionario/hora-extra'
        },
      ]
    }

    if(this.keyclaockService.hasRole('USER')){
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
