import { inject, Injectable } from '@angular/core';
import { LoanApplication } from '../shared/model/loan-application.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { delay, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApplyLoanService {
  private startInstanceUrl: string =
    '/engine-rest/process-definition/key/LOAN_APPLICATION_PROCESS/start';
  // private startInstanceUrl =
  // '/engine-rest/process-definition/key/LOAN_APPLICATION_PROCESS/startForm';
  private httpClient = inject(HttpClient);

  applyLoan(loanData: LoanApplication): Observable<unknown> {
    const body = {
      variables: {
        applicantName: { value: loanData.applicantName, type: 'String' },
        applicantEmailId: { value: loanData.applicantEmailId, type: 'String' },
        loanAmount: { value: loanData.loanAmount, type: 'Double' },
      },
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json',
    });

    return this.httpClient.post(this.startInstanceUrl, body, { headers });
  }
}
