import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { KeyCloackService } from '../services/keycloack.service';


@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

    constructor(private keycloackService: KeyCloackService, private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const expectedRole = route.data['role'];

        if (!this.keycloackService.isAuthenticated()) {
            this.router.navigate(['/login']);
            return false;
        }

        if (expectedRole && !this.keycloackService.hasRole(expectedRole)) {
            console.log('aq')
            this.router.navigate(['/no-permission']);
            return false;
        }

        return true;
    }
}
