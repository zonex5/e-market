import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {MessagesModule} from "primeng/messages";
import {NgIf} from "@angular/common";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {Message, SharedModule} from "primeng/api";
import {TranslateModule} from "@ngx-translate/core";
import {AuthService} from "../../service/auth.service";
import {TLoginUserData} from "../../types/TLoginUserData";
import {MutationSaveCustomerData} from "../../graphql/mutation.service";

@Component({
  selector: 'app-profile-dialog',
  standalone: true,
  imports: [
    ButtonModule,
    DialogModule,
    InputTextModule,
    MessagesModule,
    NgIf,
    ReactiveFormsModule,
    SharedModule,
    TranslateModule
  ],
  templateUrl: './profile-dialog.component.html',
  styleUrl: './profile-dialog.component.css'
})
export class ProfileDialogComponent implements OnInit {

  @Input() visible: boolean
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  dataForm = this.fb.group({
    email: [this.authService.email, [Validators.required, Validators.email]],
    firstName: [this.authService.firstName, [Validators.required]],
    lastName: [this.authService.lastName, Validators.required],
  })

  messages: Message[] = []

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private saveCustomerData: MutationSaveCustomerData) {
  }

  ngOnInit(): void {

  }

  get firstname() {
    return this.dataForm.controls['firstName']
  }

  get lastname() {
    return this.dataForm.controls['lastName']
  }

  get email() {
    return this.dataForm.controls['email']
  }

  onHide() {
    this.messages = []
    this.visibleChange.emit(false)
  }

  submit() {
    this.messages = []
    console.log(this.dataForm.value)
    if (this.dataForm.valid) {
      this.saveCustomerData
        .mutate({data: this.dataForm.value})
        .subscribe({
          next: ({data}) => {
            this.authService.saveUserName(this.dataForm.value.firstName!, this.dataForm.value.lastName!)
            location.reload()
          },
          error: () => {
            this.messages = [{severity: 'error', detail: 'An unknown error has occurred, please try again!'}]
          }
        })
    }
  }
}
