import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/core';
import { MatButton } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Task } from '../shared/model/task.model';
import { ApplyLoanService } from '../apply-loan/apply-loan.service';
import { LoaderService } from '../shared/service/loader.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, finalize, Observable, of, tap, throwError } from 'rxjs';
import { LoanApplication } from '../shared/model/loan-application.model';
import { TaskService } from '../shared/service/task.service';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { AsyncPipe, NgForOf, NgIf } from '@angular/common';
import { LoaderComponent } from '../shared/loader/loader.component';

@Component({
  selector: 'camunda-loan-review',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    MatError,
    MatInput,
    MatButton,
    MatCard,
    MatCardTitle,
    MatCardContent,
    NgForOf,
    AsyncPipe,
    LoaderComponent,
  ],
  templateUrl: './loan-review.component.html',
  styleUrl: './loan-review.component.scss',
})
export class LoanReviewComponent implements OnInit {
  private route: ActivatedRoute = inject(ActivatedRoute);
  task!: Task;
  taskId!: string;

  private fb = inject(FormBuilder);
  private taskService = inject(TaskService);
  private loaderService = inject(LoaderService);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);

  loading$ = this.loaderService.loading$;
  submit$: Observable<unknown> = of();

  reviewForm = this.fb.group({
    loanStatus: ['', Validators.required],
    loanStatusReason: [''],
  });

  approvalOptions = [
    { value: 'APPROVED', label: 'Yes' },
    { value: 'REJECTED', label: 'No' },
  ];

  ngOnInit() {
    this.task = this.route.snapshot.data['application'];
    this.taskId = this.route.snapshot.paramMap.get('id')!;
  }

  onSubmit() {
    if (this.reviewForm.valid) {
      const loanData: LoanApplication = this.reviewForm.value as LoanApplication;

      this.loaderService.show();
      //
      this.submit$ = this.taskService.reviewLoan(this.taskId, loanData).pipe(
        tap((_) => {
          this.snackBar.open('Application reviewed successfully', '', {
            duration: 3000,
            verticalPosition: 'top',
          });
          this.router.navigate(['/view-applications']);
        }),
        catchError((err) => {
          console.error(err);
          this.snackBar.open('Failed to review the application', '', {
            duration: 3000,
            verticalPosition: 'top',
          });
          return throwError(() => err);
        }),
        finalize(() => this.loaderService.hide()),
      );
    }
  }
}
