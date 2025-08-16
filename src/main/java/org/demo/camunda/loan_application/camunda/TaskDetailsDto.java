package org.demo.camunda.loan_application.camunda;

import org.camunda.bpm.engine.rest.dto.task.TaskDto;

public class TaskDetailsDto extends TaskDto {
	private String applicantName;
	private Double loanAmount;

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
}
