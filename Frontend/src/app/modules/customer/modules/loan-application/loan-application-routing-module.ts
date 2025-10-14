import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllApplications } from './pages/all-applications/all-applications';
import { AppliedApplicationDetails } from './pages/applied-application-details/applied-application-details';
import { NewLoanApplication } from './pages/new-loan-application/new-loan-application';
import { UploadDocuments } from './pages/upload-documents/upload-documents';
import { CheckEligibility } from './pages/check-eligibility/check-eligibility';
import { ApplicationSubmit } from './pages/application-submit/application-submit';

const routes: Routes = [
   {
    path:"",
    component:AllApplications
  },
  {
    path:":id",
    component:AppliedApplicationDetails
  },
  {
    path:"apply",
    component:NewLoanApplication
  },
  {
    path:"upload-document",
    component:UploadDocuments
  },
  {
    path:"check-eligibility",
    component:CheckEligibility
  },
  {
    path:"submit",
    component:ApplicationSubmit
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoanApplicationRoutingModule { }
