import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllOfficers } from './pages/all-officers/all-officers';
import { AddNewOfficer } from './pages/add-new-officer/add-new-officer';
import { OfficerDetails } from './pages/officer-details/officer-details';
import { UpdateOfficerDetails } from './pages/update-officer-details/update-officer-details';
import { OfficerHandleLoans } from './pages/officer-handle-loans/officer-handle-loans';

const routes: Routes = [
  {
    path:"",
    component:AllOfficers
  },
  {
    path: "add",
    component: AddNewOfficer
  },
  {
    path: ":id",
    component: OfficerDetails
  },
  {
    path: ":id/update",
    component: UpdateOfficerDetails
  },
  {
    path: ":id/loans",
    component: OfficerHandleLoans
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfficerRoutingModule { }
