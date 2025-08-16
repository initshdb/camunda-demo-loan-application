import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { AsyncPipe, NgIf } from '@angular/common';
import { LoanApplication } from '../shared/model/loan-application.model';
import { MatButton } from '@angular/material/button';
import { ApplyLoanService } from './apply-loan.service';
import { LoaderService } from '../shared/service/loader.service';
import { catchError, finalize, tap, throwError, of, EMPTY, Observable, delay } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { LoaderComponent } from '../shared/loader/loader.component';

@Component({
  selector: 'camunda-apply-loan',
  imports: [
    MatCard,
    MatCardTitle,
    MatCardContent,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    NgIf,
    MatButton,
    AsyncPipe,
    LoaderComponent,
  ],
  templateUrl: './apply-loan.component.html',
  styleUrl: './apply-loan.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ApplyLoanComponent {
  private fb = inject(FormBuilder);
  private applyLoanService = inject(ApplyLoanService);
  private loaderService = inject(LoaderService);
  private snackBar = inject(MatSnackBar);

  loading$ = this.loaderService.loading$;
  submit$: Observable<unknown> = of();

  loanForm = this.fb.group({
    applicantName: ['', Validators.required],
    applicantEmailId: ['', [Validators.required, Validators.email]],
    loanAmount: this.fb.control<number | null>(null, [Validators.required, Validators.min(1)]),
  });

  onSubmit() {
    if (this.loanForm.valid) {
      const loanData: LoanApplication = this.loanForm.value as LoanApplication;

      this.loaderService.show();

      this.submit$ = this.applyLoanService.applyLoan(loanData).pipe(
        tap((_) => {
          this.snackBar.open('Application submitted successfully', '', {
            duration: 3000,
            verticalPosition: 'top',
          });
          this.loanForm.reset();
          this.loanForm.markAsPristine();
          this.loanForm.markAsUntouched();
          // this.loanForm.controls.loanAmount.reset();
          // this.loanForm.controls.applicantName.reset();
          // this.loanForm.controls.applicantEmailId.reset();
          this.loanForm.controls.loanAmount.setErrors(null);
          this.loanForm.controls.applicantName.setErrors(null);
          this.loanForm.controls.applicantEmailId.setErrors(null);
        }),
        catchError((err) => {
          console.error(err);
          this.snackBar.open('Failed to submit the application', '', {
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
