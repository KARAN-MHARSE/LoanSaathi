import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllApplications } from './all-applications/all-applications';
import { ApplicationDetails } from './application-details/application-details';

const routes: Routes = [
{
  path:"",
  component:AllApplications
},
{
  path:"all",
  component:AllApplications
},
{
  path:"details/:applicationId",
  component:ApplicationDetails
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoanApplicationRoutingModule { }
