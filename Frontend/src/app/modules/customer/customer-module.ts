import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing-module';
import { Dashboard } from './pages/dashboard/dashboard';
import { NewLoanApplication } from './pages/new-loan-application/new-loan-application';
import { UploadDocuments } from './pages/upload-documents/upload-documents';
import { ApplicationSubmit } from './pages/application-submit/application-submit';
import { MyLoans } from './pages/my-loans/my-loans';
import { LoanDetails } from './pages/loan-details/loan-details';
import { ViewInstallments } from './pages/view-installments/view-installments';
import { Payment } from './pages/payment/payment';
import { Profile } from './pages/profile/profile';
import { CheckEligibility } from './pages/check-eligibility/check-eligibility';
import { RouterModule } from '@angular/router';


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
    CheckEligibility
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    RouterModule
  ]
})
export class CustomerModule { }
