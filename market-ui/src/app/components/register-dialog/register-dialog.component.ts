import {Component} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {AbstractControl, AbstractControlOptions, FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {MessagesModule} from "primeng/messages";
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Message, SharedModule} from "primeng/api";
import {TranslateModule} from "@ngx-translate/core";
import {AuthService} from "../../service/auth.service";
import {TLoginUserData} from "../../types/TLoginUserData";

@Component({
  selector: 'app-register-dialog',
  standalone: true,
  imports: [
    ButtonModule,
    DialogModule,
    FormsModule,
    InputTextModule,
    MessagesModule,
    NgIf,
    ReactiveFormsModule,
    RouterLink,
    SharedModule,
    TranslateModule
  ],
  templateUrl: './register-dialog.component.html',
  styleUrl: './register-dialog.component.css'
})
export class RegisterDialogComponent {

  passwordMatchValidator = (control: AbstractControl): { [key: string]: boolean } | null => {
    const password = control.get('password');
    const confirmPassword = control.get('repassword');
    return password && confirmPassword && password.value !== confirmPassword.value ? {'passwordMismatch': true} : null;
  };

  registerForm = this.fb.group({
    username: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    repassword: ['', Validators.required]
  }, {validator: this.passwordMatchValidator})  //as AbstractControlOptions

  messages: Message[] = []

  constructor(protected authService: AuthService, private fb: FormBuilder) {
  }

  get username() {
    return this.registerForm.controls['username'];
  }

  get password() {
    return this.registerForm.controls['password'];
  }

  private get repassword() {
    return this.registerForm.controls['repassword'];
  }

  get repassInvalid() {
    return (this.repassword.invalid || this.repassMismatchInvalid) && (this.repassword.dirty || this.repassword.touched)
  }

  get repassRequiredInvalid() {
    return this.repassword.errors?.['required']
  }

  get repassMismatchInvalid() {
    return this.registerForm.errors?.['passwordMismatch']
  }

  onHide() {
    this.messages = []
  }

  switchToLogin() {
    this.registerForm.reset()
    this.authService.showLoginDialog()
  }

  onInputFocused() {
    this.messages = []
  }

  registerUser() {
    this.messages = []

    this.authService.register(this.registerForm.value as TLoginUserData)
      .subscribe({
        next: (user) => {
          switch (user.loginResult) {
            case 'USER_ALREADY_EXISTS':
              this.messages = [{severity: 'error', detail: 'The specified user already exists!'}]
              break
            case 'SUCCESS':
              location.reload()
              break
            default:
              this.messages = [{severity: 'error', detail: 'The unknown error, please try again!'}]
              break
          }
        },
        error: () => {
          this.messages = [{severity: 'error', detail: 'The server in unavailable, please try again!'}]
        }
      })
  }
}
