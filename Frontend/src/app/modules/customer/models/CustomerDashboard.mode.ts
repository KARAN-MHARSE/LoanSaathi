import { LoanApplication } from "../../../shared/models/LoanApplication.model";

export class CustomerDashboard {
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    dateOfBirth: Date;
    profileUrl: string;

    totalApplications: number;
    pendingApplications: number;
    approvedApplications: number;
    rejectedApplications: number;

    totalLoanAmount: number;
    totalOutstandingAmount: number;
    totalPaidAmount: number;
    nextEMIAmount: number;
    nextEMIDueDate: Date;
    activeLoans: number;
    closedLoans: number;

    applications: LoanApplication[];

    constructor(
        firstName: string = '',
        lastName: string = '',
        email: string = '',
        phoneNumber: string = '',
        dateOfBirth: Date = new Date(),
        profileUrl: string = '',
        totalApplications: number = 0,
        pendingApplications: number = 0,
        approvedApplications: number = 0,
        rejectedApplications: number = 0,
        totalLoanAmount: number = 0,
        totalOutstandingAmount: number = 0,
        totalPaidAmount: number = 0,
        nextEMIAmount: number = 0,
        nextEMIDueDate: Date = new Date(),
        activeLoans: number = 0,
        closedLoans: number = 0,
        applications: LoanApplication[] = []
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.profileUrl = profileUrl;
        this.totalApplications = totalApplications;
        this.pendingApplications = pendingApplications;
        this.approvedApplications = approvedApplications;
        this.rejectedApplications = rejectedApplications;
        this.totalLoanAmount = totalLoanAmount;
        this.totalOutstandingAmount = totalOutstandingAmount;
        this.totalPaidAmount = totalPaidAmount;
        this.nextEMIAmount = nextEMIAmount;
        this.nextEMIDueDate = nextEMIDueDate;
        this.activeLoans = activeLoans;
        this.closedLoans = closedLoans;
        this.applications = applications;
    }
}