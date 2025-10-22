import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class KeyCloackService {
  private keycloakUrl = 'http://localhost:8081/realms/folha-facil-realm/protocol/openid-connect/token';
  private clientId = 'folha-facil-app'; 
  private clientSecret = 'xjLWnPHFsMmc61h6ZiBRKZDmuA4yzZTe'; 
  private storageKey = 'access_token';

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string) {
    const body = new HttpParams()
      .set('client_id', this.clientId)
      .set('grant_type', 'password')
      .set('username', username)
      .set('password', password)
      .set('client_secret', this.clientSecret)

    return this.http.post<any>(this.keycloakUrl, body).subscribe({
      next: (res) => {
        localStorage.setItem(this.storageKey, res.access_token);
        localStorage.setItem('refresh_token', res.refresh_token);
        this.router.navigate(['/main']);
      },
      error: (err) => {
        console.error('Erro no login', err);
      }
    });
  }

  logout() {
    localStorage.removeItem(this.storageKey);
    localStorage.removeItem('refresh_token');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    if (typeof window === "undefined") return null;
    return localStorage.getItem(this.storageKey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private getDecodedToken(): any {
    const token = this.getToken();
    if (!token) return null;

    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));
  }

  hasRole(role: string): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded) return false;

    const realmRoles = decoded?.realm_access?.roles || [];

    const resourceRoles = decoded?.resource_access?.[this.clientId]?.roles || [];

    return [...realmRoles, ...resourceRoles].includes(role);
  }

  hasRoles(requiredRoles: string[]): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded) return false;

    const realmRoles = decoded?.realm_access?.roles || [];
    const resourceRoles = decoded?.resource_access?.[this.clientId]?.roles || [];

    const userRoles = [...realmRoles, ...resourceRoles];

    return requiredRoles.every(role => userRoles.includes(role));
  }

  
  hasAnyRole(requiredRoles: string[]): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded) return false;

    const realmRoles = decoded?.realm_access?.roles || [];
    const resourceRoles = decoded?.resource_access?.[this.clientId]?.roles || [];

    const userRoles = [...realmRoles, ...resourceRoles];

    return requiredRoles.some(role => userRoles.includes(role));
  }


  getUserRoles(): string[] {
    const decoded = this.getDecodedToken();
    if (!decoded) return [];

    const realmRoles = decoded?.realm_access?.roles || [];
    const resourceRoles = decoded?.resource_access?.[this.clientId]?.roles || [];

    return [...realmRoles, ...resourceRoles];
  }

  getFirstName(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.given_name || null;
  }

  getLastName(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.family_name || null;
  }

  getFullName(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.name || null;
  }
}
