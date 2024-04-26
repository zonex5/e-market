import {Component, OnInit} from '@angular/core';
import {DialogModule} from "primeng/dialog";
import {AvatarModule} from "primeng/avatar";
import {ButtonModule} from "primeng/button";
import {AutoFocusModule} from "primeng/autofocus";
import {AuthService} from "../../service/auth.service";
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {CommonModule} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {TLoginUserData} from "../../types/TLoginUserData";
import {Message, MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {MessagesModule} from "primeng/messages";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: 'app-login-dialog',
  standalone: true,
  imports: [
    DialogModule,
    AvatarModule,
    ButtonModule,
    AutoFocusModule,
    CardModule,
    InputTextModule,
    PaginatorModule,
    ReactiveFormsModule,
    RouterLink,
    ToastModule,
    MessagesModule,
    CommonModule,
    TranslateModule
  ],
  providers: [MessageService],
  templateUrl: './login-dialog.component.html',
  styleUrl: './login-dialog.component.css'
})
export class LoginDialogComponent implements OnInit {

  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  messages: Message[] = []

  constructor(protected authService: AuthService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
  }

  onHide() {
    this.messages = []
  }

  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  switchToRegister() {
    this.loginForm.reset()
    this.authService.showRegisterDialog()
  }

  loginUser() {
    this.messages = []
    this.authService.login(this.loginForm.value as TLoginUserData)
      .subscribe({
        next: (user) => {
          switch (user.loginResult) {
            case 'USER_NOT_FOUND':
            case 'INVALID_PASSWORD':
              this.messages = [{severity: 'error', detail: 'The user name or password is incorrect!'}]
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
