import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminRoutingModule } from './admin-routing-module';
import { AddLoanScheme } from './modules/loan-scheme/pages/add-loan-scheme/add-loan-scheme';
import { AllLoanSchemes } from './modules/loan-scheme/pages/all-loan-schemes/all-loan-schemes';
import { LoanSchemeDetail } from './modules/loan-scheme/pages/loan-scheme-detail/loan-scheme-detail';
import { UpdateLoanScheme } from './modules/loan-scheme/pages/update-loan-scheme/update-loan-scheme';
import { AddNewOfficer } from './modules/officer/pages/add-new-officer/add-new-officer';
import { AllOfficers } from './modules/officer/pages/all-officers/all-officers';
import { OfficerDetails } from './modules/officer/pages/officer-details/officer-details';
import { OfficerHandleLoans } from './modules/officer/pages/officer-handle-loans/officer-handle-loans';
import { UpdateOfficerDetails } from './modules/officer/pages/update-officer-details/update-officer-details';
import { Dashboard } from './pages/dashboard/dashboard';
import { Profile } from './pages/profile/profile';


@NgModule({
  declarations: [
    Dashboard,
    AddLoanScheme,
    AllLoanSchemes,
    LoanSchemeDetail,
    UpdateLoanScheme,
    AllOfficers,
    OfficerDetails,
    AddNewOfficer,
    Profile,
    UpdateOfficerDetails,
    OfficerHandleLoans
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class AdminModule { }
