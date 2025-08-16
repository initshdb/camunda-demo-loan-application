import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource,
} from '@angular/material/table';
import { MatButton } from '@angular/material/button';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Task } from '../shared/model/task.model';

@Component({
  selector: 'camunda-view-applications',
  imports: [
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatButton,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    RouterLink,
  ],
  templateUrl: './view-applications.component.html',
  styleUrl: './view-applications.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ViewApplicationsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'action'];

  private route: ActivatedRoute = inject(ActivatedRoute);
  tasksDS = new MatTableDataSource<Task>([]);

  ngOnInit() {
    this.tasksDS.data = this.route.snapshot.data['applications'];
  }
}
