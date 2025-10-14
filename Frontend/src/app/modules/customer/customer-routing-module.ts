import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { NewLoanApplication } from './modules/loan-application/pages/new-loan-application/new-loan-application';
import { UploadDocuments } from './modules/loan-application/pages/upload-documents/upload-documents';
import { CheckEligibility } from './modules/loan-application/pages/check-eligibility/check-eligibility';
import { ApplicationSubmit } from './modules/loan-application/pages/application-submit/application-submit';
import { MyLoans } from './modules/current-loans/pages/my-loans/my-loans';
import { LoanDetails } from './modules/current-loans/pages/loan-details/loan-details';
import { Profile } from './pages/profile/profile';
import { AllApplications } from './modules/loan-application/pages/all-applications/all-applications';
import { AppliedApplicationDetails } from './modules/loan-application/pages/applied-application-details/applied-application-details';
import { Payment } from './modules/current-loans/pages/payment/payment';

const routes: Routes = [
  {
    path:"",
    redirectTo:"dashboard",
    pathMatch:"full"
  },
  {
    path:"dashboard",
    component:Dashboard
  },
  {
    path:"profile",
    component:Profile
  },
  {
    path:"loans",
    loadChildren:()=>{
      return import('./modules/current-loans/current-loans-module').then(m=>m.CurrentLoansModule);
    }
  },
  {
    path:"applications",
    loadChildren:()=>{
      return import('./modules/loan-application/loan-application-module').then(m=>m.LoanApplicationModule);
    }
  },
  

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
 