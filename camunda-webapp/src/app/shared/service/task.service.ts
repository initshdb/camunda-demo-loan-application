import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoanApplication } from '../model/loan-application.model';
import { map, Observable } from 'rxjs';
import { Task } from '../model/task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private taskUrl: string = '/api/engine-rest/task';
  private httpClient = inject(HttpClient);

  tasks(): Observable<Task> {
    const body = {
      candidateUser: 'demo',
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json',
    });

    return this.httpClient.post<Task>(this.taskUrl, body, { headers });
  }

  task(id: string): Observable<Task> {
    const body = {
      taskId: id,
      candidateUser: 'demo',
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json',
    });

    return this.httpClient
      .post<Task[]>(this.taskUrl, body, { headers })
      .pipe<Task>(map((tasks) => tasks[0]));
  }

  reviewLoan(taskId: string, loanData: LoanApplication): Observable<unknown> {
    const body = {
      variables: {
        loanStatus: { value: loanData.loanStatus, type: 'String' },
        loanStatusReason: { value: loanData.loanStatusReason, type: 'String' },
      },
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json',
    });

    const taskSubmitUrl = `/engine-rest/task/${taskId}/submit-form`;
    return this.httpClient.post(taskSubmitUrl, body, { headers });
  }
}
