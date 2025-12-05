import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule, } from "@angular/forms";
import { Auth } from '../../models/auth.model';
import { KeyCloakService } from '../../services/keycloak.service';

@Component({
  selector: 'app-login-page',
  imports: [
    MatIconModule, 
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css'
})

export class LoginPage {
  hide = true

	authForm: FormGroup;
  KeyCloakService = inject(KeyCloakService);

  constructor(private fb: FormBuilder) {
		this.authForm = this.fb.group({
			user: [],
      pass: []
		});
	}

  getAuthForm(){
    const valeu = this.authForm.value

    const auth : Auth = valeu

    return auth
  }

  auth(){
    const login : Auth = this.getAuthForm()
    this.KeyCloakService.login(login.user, login.pass)
  }

}
