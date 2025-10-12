import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path:'auth',
    loadChildren:()=>{
      return import('./modules/auth/auth-module').then(m=> m.AuthModule);
    }
  },
  {
    path:'admin',
    loadChildren:()=>{
      return import('./modules/admin/admin-module').then(m=>m.AdminModule)
    }
  },
  {
    path:'officer',
    loadChildren:()=>{
      return import('./modules/officer/officer-module').then(m=>m.OfficerModule)
    }
  },
  {
    path:'customer',
    loadChildren:()=>{
      return import('./modules/customer/customer-module').then(m=>m.CustomerModule)
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
