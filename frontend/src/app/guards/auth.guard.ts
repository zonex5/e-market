import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../service/auth.service";

export const authGuard: CanActivateFn = () => {

  const router = inject(Router)
  const authService = inject(AuthService)

  if (!authService.authenticated) {
    router.navigate(['/main'])
    return false
  }
  return true
}
