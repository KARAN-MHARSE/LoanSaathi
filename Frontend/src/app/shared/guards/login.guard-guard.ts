import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../modules/auth/services/auth.service';

@Injectable({
  providedIn: 'root'
})

export class LoginGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { };
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    console.log("start")
    if (this.authService.isAuthenticated()) {
      const userRole = this.authService.getUserRole();

      const dashboardRoute = userRole === 'ROLE_ADMIN' ? "admin" : userRole === "ROLE_OFFICER" ? "officer" : "customer";
      this.router.navigate([dashboardRoute])
      return false;
    }
    return false;
  }
}