import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../modules/auth/services/auth.service';

@Injectable({
  providedIn:'root'
})

export class AuthGuard implements CanActivate{

  constructor(
    private authService:AuthService,
    private router:Router
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.authService.isAuthenticated()){
      return true;
    }

    this.router.navigate(['/auth/login'])
    return false;
  }
}