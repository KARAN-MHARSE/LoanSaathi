import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoanApplicationResponseDto, OfficerService } from '../../../services/officer.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-all-applications',
  standalone: false,
  templateUrl: './all-applications.html',
  styleUrls: ['./all-applications.css'],
})
export class AllApplications implements OnInit, OnDestroy {
  // --- Filtering Properties ---
  selectedStatus: string = 'ALL';
  selectedScheme: string = 'ALL';
  schemeNames: string[] = [];

  // --- Data Properties ---
  masterApplications: LoanApplicationResponseDto[] = [];
  applications: LoanApplicationResponseDto[] = [];

  // --- UI/State Properties ---
  isLoading: boolean = true;
  currentPage: number = 0;
  pageSize: number = 10;

  private statusUpdateSubscription: Subscription | undefined;

  constructor(private router: Router, private loanOfficerService: OfficerService) {}

  ngOnInit(): void {
    this.fetchLoanSchemes();
    this.fetchAssignedApplications();

    this.statusUpdateSubscription = this.loanOfficerService.applicationStatusUpdated$.subscribe(
      (updated) => {
        if (updated) {
          this.fetchAssignedApplications();
        }
      }
    );
  }

  ngOnDestroy(): void {
    if (this.statusUpdateSubscription) {
      this.statusUpdateSubscription.unsubscribe();
    }
  }

  fetchLoanSchemes(): void {
    this.loanOfficerService.getLoanSchemes().subscribe({
      next: (response) => {
        this.schemeNames = response.content.map((scheme) => scheme.schemeName);
      },
      error: (err) => {
        console.error('Failed to fetch loan schemes for filter', err);
      },
    });
  }

  fetchAssignedApplications(): void {
    this.isLoading = true;
    this.loanOfficerService.getAssignedApplications(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.masterApplications = response.content;
        this.applications = [...this.masterApplications];
        this.isLoading = false;
        this.applyFilters();
      },
      error: (err) => {
        console.error('Failed to fetch assigned applications', err);
        this.isLoading = false;
      },
    });
  }

  applyFilters(): void {
    let filteredList = this.masterApplications;

    // 1. Filter by Status
    if (this.selectedStatus !== 'ALL') {
      filteredList = filteredList.filter((app) => app.applicationStatus === this.selectedStatus);
    }

    // 2. Filter by Loan Scheme
    if (this.selectedScheme !== 'ALL') {
      filteredList = filteredList.filter((app) => app.loanSchemeName === this.selectedScheme);
    }

    this.applications = filteredList;
  }

  goToDetails(applicationId: number): void {
    this.router.navigate(['/officer/application/details', applicationId]);
  }
}
