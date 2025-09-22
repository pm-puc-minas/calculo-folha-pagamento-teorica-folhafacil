import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MiniMenuNavItem } from '../../models/router.model';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-mini-menu-nav',
  imports: [CommonModule, RouterModule],
  templateUrl: './mini-menu-nav.html',
  styleUrl: './mini-menu-nav.css'
})
export class MiniMenuNav {

  @Input({ required: true }) routes!: MiniMenuNavItem;
  open = false; 

  toggleMenu() {
    this.open = !this.open;
  }
}
