import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { ResetPassword } from './pages/reset-password/reset-password';
import { ForgotPassword } from './pages/forgot-password/forgot-password';

const routes: Routes = [
  {
    path:"",
    redirectTo:"login",
    pathMatch:'full'
  },
  {
    path:"login",
    component:Login
  },
  {
    path:"register",
    component:Register
  },
  {
    path:"reset-password",
    component:ResetPassword
  },
  {
    path:"forgot-password",
    component:ForgotPassword
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
