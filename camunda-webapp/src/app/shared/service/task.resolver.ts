import { ResolveFn } from '@angular/router';
import { TaskService } from './task.service';
import { inject } from '@angular/core';
import { Task } from '../model/task.model';

export const taskResolver: ResolveFn<Task> = (route) => {
  const taskService = inject(TaskService);
  const taskId = route.paramMap.get('id')!;
  return taskId ? taskService.task(taskId) : taskService.tasks();
};
