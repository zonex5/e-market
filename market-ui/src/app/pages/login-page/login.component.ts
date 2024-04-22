import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from "../../service/auth.service";
import {ButtonModule} from "primeng/button";
import {CardModule} from "primeng/card";
import {CommonModule} from "@angular/common";
import {InputTextModule} from "primeng/inputtext";
import {MessageService} from "primeng/api";
import {TLoginUserData} from "../../types/TLoginUserData";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    ButtonModule, FormsModule, ReactiveFormsModule, CardModule, CommonModule, InputTextModule, RouterLink, ToastModule
  ],
  providers: [MessageService],
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    if (this.authService.authenticated) {
      this.router.navigate(['/main'])
    }
  }

  loginUser() {
    this.authService.login(this.loginForm.value as TLoginUserData)
      .subscribe({
        next: (user) => {
          switch (user.loginResult) {
            case 'USER_NOT_FOUND':
            case 'INVALID_PASSWORD':
              this.messageService.add({severity: 'warn', summary: 'Attention', detail: 'The user name or password is incorrect!'});
              break
            case 'SUCCESS':
              this.router.navigate(['/main'])
              break
            default:
              this.messageService.add({severity: 'error', summary: 'Error', detail: 'The unknown error, please try again!'});
              break
          }
        },
        error: () => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'The server in unavailable, please try again!'});
        }
      })
  }
}
