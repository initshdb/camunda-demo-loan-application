import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ApplyLoanComponent } from './apply-loan/apply-loan.component';
import { ViewApplicationsComponent } from './view-applications/view-applications.component';
import { taskResolver } from './shared/service/task.resolver';
import { LoanReviewComponent } from './loan-review/loan-review.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'apply', component: ApplyLoanComponent },
  {
    path: 'view-applications',
    component: ViewApplicationsComponent,
    resolve: { applications: taskResolver },
  },
  {
    path: 'loan-review/:id',
    component: LoanReviewComponent,
    resolve: { application: taskResolver },
  },
];
