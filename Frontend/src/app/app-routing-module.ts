import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Unauthorized } from './pages/unauthorized/unauthorized';
import { LoginGuard } from './shared/guards/login.guard-guard';
import { AuthGuard } from './shared/guards/auth.guard-guard';
import { RoleGuard } from './shared/guards/role.guard-guard';

const routes: Routes = [
  {
    path: '',
    redirectTo:"auth",
    pathMatch:'full'
  },
  {
    path: 'auth',
    canActivate: [LoginGuard],
    loadChildren: () => {
      return import('./modules/auth/auth-module').then(m => m.AuthModule);
    }
  },
  {
    path: 'admin',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_ADMIN'] },
    loadChildren: () => {
      return import('./modules/admin/admin-module').then(m => m.AdminModule)
    }
  },
  {
    path: 'officer',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_OFFICER'] },
    loadChildren: () => {
      return import('./modules/officer/officer-module').then(m => m.OfficerModule)
    }
  },
  {
    path: 'customer',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_CUSTOMER'] },
    loadChildren: () => {
      return import('./modules/customer/customer-module').then(m => m.CustomerModule)
    }
  },
  {
    path: "unauthorized",
    component: Unauthorized
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
