package org.demo.camunda.loan_application.camunda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.SortingDto;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.camunda.bpm.engine.rest.util.QueryUtil;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.runtime.VariableInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.demo.camunda.loan_application.camunda.TaskDetailsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "loanAppTaskService")
public class TaskService {

	@Value("${org.demo.camunda.loan-application.process-definitions}")
	private String processDefinitionKey;

	private final org.camunda.bpm.engine.TaskService cTaskSerivice;
	private final ProcessEngine processEngine;

	private final ObjectMapper mapper = new ObjectMapper();

	public TaskService(org.camunda.bpm.engine.TaskService cTaskSerivice, ProcessEngine processEngine) {
		this.cTaskSerivice = cTaskSerivice;
		this.processEngine = processEngine;
	}


	public List<TaskDetailsDto> findTasks(TaskQueryDto queryDto) {

		queryDto.setObjectMapper(mapper);

		if (queryDto.getProcessDefinitionKey() == null) {
			queryDto.setProcessDefinitionKey(processDefinitionKey);
		}

		List<SortingDto> sortByList = new ArrayList<>();
		SortingDto sortingDto = new SortingDto();
		sortingDto.setSortBy("created");
		sortingDto.setSortOrder("desc");
		sortByList.add(sortingDto);

		queryDto.setSorting(sortByList);

		TaskQuery query = queryDto.toQuery(processEngine);
		query.initializeFormKeys();

		List<Task> tasks = QueryUtil.list(query, null, null);

		return tasks.stream().map(TaskDetailsDto::fromEntity).map(this::mapToTaskDetails).map(this::mapVariables).toList();
	}

	private TaskDetailsDto mapToTaskDetails(TaskDto dto) {
		String value = null;
		try {
			value = mapper.writeValueAsString(dto);
			return mapper.readValue(value, TaskDetailsDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private TaskDetailsDto mapVariables(TaskDetailsDto task) {
		VariableInstanceQuery query = processEngine.getRuntimeService()
					 .createVariableInstanceQuery()
					 .disableCustomObjectDeserialization()
					 .variableNameIn("applicantName", "loanAmount")
					 .processInstanceIdIn(task.getProcessInstanceId());

		List<VariableInstance> variableInstances = QueryUtil.list(query, null, null);

		Map<String, Object> variableMap = variableInstances.stream().collect(Collectors.toMap(VariableInstance::getName, VariableInstance::getValue));

		task.setApplicantName((String)variableMap.get("applicantName"));
		task.setLoanAmount((Double)variableMap.get("loanAmount"));
		return task;
	}


	/*private TaskDto mapAdditionalTaskDetails(Task task) {


	}

	private void includeFormKey(Task task, TaskDto taskDto) {
		String formKey = task.getCamundaFormRef().getKey();
		taskDto.
	}*/

}
