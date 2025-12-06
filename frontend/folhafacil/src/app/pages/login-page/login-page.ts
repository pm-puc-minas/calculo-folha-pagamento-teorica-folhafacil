import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { Auth } from '../../models/auth.model';
import { KeyCloakService } from '../../services/keycloak.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  imports: [MatIconModule, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  hide = true;

  authForm: FormGroup;
  KeyCloakService = inject(KeyCloakService);

  constructor(private fb: FormBuilder, private router: Router) {
    this.authForm = this.fb.group({
      user: [],
      pass: [],
    });
  }

  getAuthForm() {
    const valeu = this.authForm.value;

    const auth: Auth = valeu;

    return auth;
  }

  auth() {
    const login: Auth = this.getAuthForm();

    this.KeyCloakService.login(login.user, login.pass).subscribe({
      next: (res) => {
        localStorage.setItem('access_token', res.access_token);
        localStorage.setItem('refresh_token', res.refresh_token);

        Swal.fire({
          icon: 'success',
          title: 'Login realizado!',
          timer: 1600,
          showConfirmButton: false,
        });

        this.router.navigate(['/main']);
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Usuário ou senha incorretos',
          text: 'Verifique os dados e tente novamente.',
        });
      },
    });
  }
}
