import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing-module';
import { Dashboard } from './pages/dashboard/dashboard';
import { NewLoanApplication } from './modules/loan-application/pages/new-loan-application/new-loan-application';
import { UploadDocuments } from './modules/loan-application/pages/upload-documents/upload-documents';
import { ApplicationSubmit } from './modules/loan-application/pages/application-submit/application-submit';
import { MyLoans } from './modules/current-loans/pages/my-loans/my-loans';
import { LoanDetails } from './modules/current-loans/pages/loan-details/loan-details';
import { ViewInstallments } from './pages/view-installments/view-installments';
import { Payment } from './modules/current-loans/pages/payment/payment';
import { Profile } from './pages/profile/profile';
import { CheckEligibility } from './modules/loan-application/pages/check-eligibility/check-eligibility';
import { RouterModule } from '@angular/router';
import { AllApplications } from './modules/loan-application/pages/all-applications/all-applications';
import { AppliedApplicationDetails } from './modules/loan-application/pages/applied-application-details/applied-application-details';


@NgModule({
  declarations: [
    Dashboard,
    NewLoanApplication,
    UploadDocuments,
    ApplicationSubmit,
    MyLoans,
    LoanDetails,
    ViewInstallments,
    Payment,
    Profile,
    CheckEligibility,
    AllApplications,
    AppliedApplicationDetails
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    RouterModule
  ]
})
export class CustomerModule { }
