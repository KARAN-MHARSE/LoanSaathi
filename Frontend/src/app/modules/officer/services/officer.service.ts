import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

export interface DocumentResponseDto {
  id: number;
  documentType: string;
  documentUrl: string;
}

export interface LoanApplicationResponseDto {
  id: number;
  applicationId: string;
  requiredAmount: number;
  annualIncome: number;
  occupation: string;
  reason: string;
  tenure: number;
  applicationStatus: LoanApplicationStatus;
  createdAt: Date;
  customerId: number;
  customerName: string;
  loanSchemeId: number;
  loanSchemeName: string;
  documents: DocumentResponseDto[];
}

export interface PageResponseDto<T> {
  content: T[];
  pageNo: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}

export interface LoanSchemeResponseDto {
  id: number;
  schemeName: string;
}

export interface LoanApplicationStatusUpdateDto {
  applicationId: number;
  status: LoanApplicationStatus;
  officerRemarks?: string;
}

export enum LoanApplicationStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED',
}

@Injectable({
  providedIn: 'root',
})
export class OfficerService {
  private apiUrl = 'http://localhost:5000/api/v1';

  private applicationStatusUpdatedSubject = new BehaviorSubject<boolean>(false);
  applicationStatusUpdated$ = this.applicationStatusUpdatedSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Method to signal that a status change has occurred
  signalStatusUpdate(): void {
    this.applicationStatusUpdatedSubject.next(true);
  }

  // 1. Fetch ALL Loan Schemes (for the filter dropdown)
  getLoanSchemes(): Observable<PageResponseDto<LoanSchemeResponseDto>> {
    const params = new HttpParams().set('pageSize', 100);
    return this.http.get<PageResponseDto<LoanSchemeResponseDto>>(`${this.apiUrl}/loan-schemes`, {
      params,
    });
  }

  // 2. Fetch Assigned Applications (for the dashboard table)
  getAssignedApplications(
    page: number = 0,
    size: number = 10
  ): Observable<PageResponseDto<LoanApplicationResponseDto>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', size.toString());

    return this.http.get<PageResponseDto<LoanApplicationResponseDto>>(
      `${this.apiUrl}/officer/applications/assigned`,
      { params }
    );
  }

  // 3. Fetch Single Application Details
  getApplicationDetails(applicationId: number): Observable<LoanApplicationResponseDto> {
    return this.http.get<LoanApplicationResponseDto>(
      `${this.apiUrl}/loan-applications/${applicationId}`
    );
  }

  // 4. Update Application Status (Approve/Reject)
  updateApplicationStatus(
    requestDto: LoanApplicationStatusUpdateDto
  ): Observable<LoanApplicationStatusUpdateDto> {
    return this.http
      .put<LoanApplicationStatusUpdateDto>(`${this.apiUrl}/officer/applications`, requestDto)
      .pipe(tap(() => this.signalStatusUpdate()));
  }
}
