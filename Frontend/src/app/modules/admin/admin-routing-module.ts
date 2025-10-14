import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { AddLoanScheme } from './modules/loan-scheme/pages/add-loan-scheme/add-loan-scheme';
import { AllLoanSchemes } from './modules/loan-scheme/pages/all-loan-schemes/all-loan-schemes';
import { LoanSchemeDetail } from './modules/loan-scheme/pages/loan-scheme-detail/loan-scheme-detail';
import { UpdateLoanScheme } from './modules/loan-scheme/pages/update-loan-scheme/update-loan-scheme';
import { AllOfficers } from './modules/officer/pages/all-officers/all-officers';
import { OfficerDetails } from './modules/officer/pages/officer-details/officer-details';
import { AddNewOfficer } from './modules/officer/pages/add-new-officer/add-new-officer';
import { Profile } from './pages/profile/profile';
import { UpdateOfficerDetails } from './modules/officer/pages/update-officer-details/update-officer-details';
import { OfficerHandleLoans } from './modules/officer/pages/officer-handle-loans/officer-handle-loans';

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
