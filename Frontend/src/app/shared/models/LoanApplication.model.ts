import { Document } from './Document.model';
import { LoanApplicationStatus } from './LoanApplicationStatus';

export class LoanApplication {
    id?: number;
    requiredAmount?: number;
    annualIncome?: number;
    occupation?: string;
    reason?: string;
    tenure?: number;
    applicationStatus?: LoanApplicationStatus;
    createdAt?: Date;
    customerId?: number;
    customerName?: string;
    loanSchemeId?: number;
    loanSchemeName?: string;
    documents?: Document[];
}