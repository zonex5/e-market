import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {TLoginUserData} from "../types/TLoginUserData";
import {environment} from "../../environments/environment"
import {TUserDetails} from "../types/TUserDetails";
import {BehaviorSubject, Observable, Subscription, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public signedUser$ = new BehaviorSubject<TUserDetails | null>(null)

  public loginDialogResult$ = new BehaviorSubject<string | null>(null)

  public loginDialogVisible = false
  public registerDialogVisible = false

  get authenticated(): boolean {
    return this.signedUser$.getValue() != null
  }

  get token(): string | null {
    return localStorage.getItem("user.token") || ''
  }

  get userFullName(): string {
    return (localStorage.getItem("user.firstName") ?? '') + (localStorage.getItem("user.lastName") ? ` ${localStorage.getItem("user.lastName")}` : '')
  }

  get firstName(): string {
    return localStorage.getItem("user.firstName") ?? ''
  }

  get lastName(): string {
    return localStorage.getItem("user.lastName") ?? ''
  }

  get email(): string {
    return localStorage.getItem("user.email") ?? ''
  }

  constructor(private http: HttpClient) {  //todo only auth or not
    if (this.userDataStored()) {
      this.signedUser$.next({
        admin: Boolean(localStorage.getItem("user.admin")),
        lastName: localStorage.getItem("user.lastName"),
        firstName: localStorage.getItem("user.firstName"),
        uuid: localStorage.getItem("user.uuid")!,
        token: localStorage.getItem("user.token")!,
        loginResult: 'SUCCESS'
      })
    }
  }

  showLoginDialog() {
    this.hideRegisterDialog()
    this.loginDialogVisible = true
  }

  hideLoginDialog() {
    this.loginDialogVisible = false
  }

  showRegisterDialog() {
    this.hideLoginDialog()
    this.registerDialogVisible = true
  }

  hideRegisterDialog() {
    this.registerDialogVisible = false
  }

  saveUserData(userData: TUserDetails) {
    localStorage.setItem('user.token', userData.token);
    localStorage.setItem('user.uuid', userData.uuid);
    localStorage.setItem('user.admin', String(userData.admin));
    if (userData.firstName) {
      localStorage.setItem('user.firstName', userData.firstName);
    }
    if (userData.lastName) {
      localStorage.setItem('user.lastName', userData.lastName);
    }
  }

  saveUserName(firstname: string, lastname: string) {
    if (firstname) {
      localStorage.setItem('user.firstName', firstname);
    }
    if (lastname) {
      localStorage.setItem('user.lastName', lastname);
    }
  }

  login(userData: TLoginUserData): Observable<TUserDetails> {  //todo - generic
    return this.http.post<TUserDetails>(`${environment.apiUrl}/security/login`, userData)
      .pipe(
        tap((userData) => {
          if (userData.loginResult === 'SUCCESS') {
            if (userData.token && userData.uuid) {
              this.saveUserData(userData)
              this.signedUser$.next(userData)
            } else {
              userData.loginResult = 'OTHER'
            }
          }
          this.loginDialogResult$.next(userData.loginResult)
        })
      )
  }

  register(userData: TLoginUserData): Observable<TUserDetails> {
    return this.http.post<TUserDetails>(`${environment.apiUrl}/security/register`, userData)
      .pipe(
        tap((userData) => {
          if (userData.loginResult === 'SUCCESS') {
            if (userData.token && userData.uuid) {
              this.saveUserData(userData)
              this.signedUser$.next(userData)
            } else {
              userData.loginResult = 'OTHER'
            }
          }
          this.loginDialogResult$.next(userData.loginResult)
        })
      )
  }

  logout() {
    return new Observable<void>(obs => {
      localStorage.removeItem("user.token")
      localStorage.removeItem("user.uuid")
      localStorage.removeItem("user.admin")
      localStorage.removeItem("user.firstName")
      localStorage.removeItem("user.lastName")
      this.signedUser$.next(null)
      obs.complete()
    })
  }

  private userDataStored() {
    return !!localStorage.getItem("user.token") && !!localStorage.getItem("user.uuid")
  }

}
