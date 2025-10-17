import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  LoanApplicationResponseDto,
  LoanApplicationStatus,
  LoanApplicationStatusUpdateDto,
  OfficerService,
} from '../../../services/officer.service';

@Component({
  selector: 'app-application-details',
  standalone: false,
  templateUrl: './application-details.html',
  styleUrl: './application-details.css',
})
export class ApplicationDetails implements OnInit {
  applicationId: number | undefined;
  application: LoanApplicationResponseDto | null = null;
  isLoading: boolean = true;

  // --- Modal Properties ---
  showRejectModal: boolean = false;
  rejectionReason: string = '';
  showValidationError: boolean = false;
  showApproveModal: boolean = false;

  LoanApplicationStatus = LoanApplicationStatus;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loanOfficerService: OfficerService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idFromRoute = params.get('applicationId');
      const parsedId = idFromRoute ? parseInt(idFromRoute, 10) : null;

      if (parsedId) {
        this.applicationId = parsedId;
        this.fetchApplicationDetails(this.applicationId);
      } else {
        console.error('Application ID not found in URL. Navigating back to dashboard.');
        this.isLoading = false;
        this.router.navigate(['/officer/application/all']);
      }
    });
  }

  fetchApplicationDetails(id: number): void {
    this.isLoading = true;
    this.loanOfficerService.getApplicationDetails(id).subscribe({
      next: (data) => {
        this.application = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to fetch application details', err);
        this.isLoading = false;
        this.router.navigate(['/officer/application/all']);
      },
    });
  }

  onApprove(): void {
    if (this.application && this.application.applicationStatus === LoanApplicationStatus.PENDING) {
      this.showApproveModal = true;
    }
  }

  confirmApprove(): void {
    if (!this.application) return;

    const request: LoanApplicationStatusUpdateDto = {
      applicationId: this.application.id,
      status: LoanApplicationStatus.APPROVED,
    };

    this.loanOfficerService.updateApplicationStatus(request).subscribe({
      next: () => {
        this.application!.applicationStatus = LoanApplicationStatus.APPROVED;
        this.showApproveModal = false;
        this.redirectToDashboard();
      },
      error: (err) => {
        console.error('Approval failed', err);
        this.showApproveModal = false;
      },
    });
  }

  cancelApprove(): void {
    this.showApproveModal = false;
  }

  onReject(): void {
    if (this.application && this.application.applicationStatus === LoanApplicationStatus.PENDING) {
      this.showRejectModal = true;
      this.rejectionReason = '';
      this.showValidationError = false;
    }
  }

  confirmReject(): void {
    if (!this.application) return;

    if (this.rejectionReason.trim().length < 10) {
      this.showValidationError = true;
      return;
    }

    this.showValidationError = false;

    const request: LoanApplicationStatusUpdateDto = {
      applicationId: this.application.id,
      status: LoanApplicationStatus.REJECTED,
      officerRemarks: this.rejectionReason,
    };

    this.loanOfficerService.updateApplicationStatus(request).subscribe({
      next: () => {
        this.application!.applicationStatus = LoanApplicationStatus.REJECTED;
        this.showRejectModal = false;
        this.redirectToDashboard();
      },
      error: (err) => {
        console.error('Rejection failed', err);
        this.showRejectModal = false;
      },
    });
  }

  cancelReject(): void {
    this.showRejectModal = false;
    this.rejectionReason = '';
    this.showValidationError = false;
  }

  redirectToDashboard(): void {
    this.router.navigate(['/officer/application/all']);
  }
}
