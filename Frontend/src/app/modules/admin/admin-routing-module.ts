import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Profile } from './pages/profile/profile';

const routes: Routes = [
  {
    path: "",
    redirectTo: "dashboard",
    pathMatch: "full"
  },
  {
    path: "dashboard",
    component: Dashboard
  },
  {
    path: "profile",
    component: Profile
  },
  {
    path:"loan-schemes",
    loadChildren:()=>{
      return import("./modules/loan-scheme/loan-scheme-module").then(m=>m.LoanSchemeModule);
    }
  },
  {
    path:"officers",
    loadChildren:()=>{
      return import("./modules/officer/officer-module").then(m=>m.OfficerModule);
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
