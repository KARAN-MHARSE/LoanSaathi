import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoanApplicationRoutingModule } from './loan-application-routing-module';
import { AllApplications } from './all-applications/all-applications';
import { ApplicationDetails } from './application-details/application-details';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AllApplications,
    ApplicationDetails
  ],
  imports: [
    CommonModule,
    LoanApplicationRoutingModule,
    FormsModule
  ]
})
export class LoanApplicationModule { }
