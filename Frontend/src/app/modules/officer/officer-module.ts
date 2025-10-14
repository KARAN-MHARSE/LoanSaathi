import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OfficerRoutingModule } from './officer-routing-module';
import { RouterModule } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Profile } from './pages/profile/profile';


@NgModule({
  declarations: [
    Dashboard,
    Profile
  ],
  imports: [
    CommonModule,
    OfficerRoutingModule,
    RouterModule
  ]
})
export class OfficerModule { }
