package org.demo.camunda.loan_application.camunda.controller;

import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.demo.camunda.loan_application.camunda.TaskDetailsDto;
import org.demo.camunda.loan_application.camunda.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/engine-rest/task")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskDetailsDto> tasks(@RequestBody(required = false) TaskQueryDto queryDto) {
		return taskService.findTasks(queryDto);
	}
}
