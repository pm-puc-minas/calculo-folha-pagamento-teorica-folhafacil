import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule, } from "@angular/forms";
import { Auth } from '../../models/auth.model';
import { KeyCloackService } from '../../services/keycloack.service';

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
  keycloackService = inject(KeyCloackService);

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
    console.log("askijdajs",this.getAuthForm())
    const login : Auth = this.getAuthForm()
    this.keycloackService.login(login.user, login.pass)
  }

}
