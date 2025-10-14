import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllLoanSchemes } from './pages/all-loan-schemes/all-loan-schemes';
import { AddLoanScheme } from './pages/add-loan-scheme/add-loan-scheme';
import { LoanSchemeDetail } from './pages/loan-scheme-detail/loan-scheme-detail';
import { UpdateLoanScheme } from './pages/update-loan-scheme/update-loan-scheme';

const routes: Routes = [
   {
      path: "",
      component: AllLoanSchemes
    },
    {
      path: "add",
      component: AddLoanScheme
    },
    {
      path: ":id",
      component: LoanSchemeDetail
    },
    {
      path: ":id/update",
      component: UpdateLoanScheme
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoanSchemeRoutingModule { }
