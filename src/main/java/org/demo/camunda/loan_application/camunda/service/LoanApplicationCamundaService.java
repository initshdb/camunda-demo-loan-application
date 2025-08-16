package org.demo.camunda.loan_application.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.demo.camunda.loan_application.domain.db.repository.LoanApplicationRepository;
import org.demo.camunda.loan_application.domain.model.LoanApplicationDto;
import org.demo.camunda.loan_application.domain.model.LoanStatus;
import org.demo.camunda.loan_application.domain.service.LoanApplicationService;
import org.springframework.stereotype.Service;

/**
 * Loan Application wrapper service called from the Process
 */
@Service(value = "loanApplicationCamundaService")
public class LoanApplicationCamundaService {

	private final LoanApplicationService loanApplicationService;

	public LoanApplicationCamundaService(LoanApplicationService loanApplicationService,
										 LoanApplicationRepository loanApplicationRepository) {
		this.loanApplicationService = loanApplicationService;
	}

	/**
	 * Save the loan application with the customer entered details
	 * @param execution
	 */
	public void registerLoanApplication(DelegateExecution execution) {
		// TODO We can also create a user for the applicant in Camunda Users

		String applicantName = (String) execution.getVariable("applicantName");
		String applicantEmailId = (String) execution.getVariable("applicantEmailId");
		Double loanAmount = (Double) execution.getVariable("loanAmount");

		LoanApplicationDto loanApplication = new LoanApplicationDto();
		loanApplication.setId(execution.getProcessInstanceId());
		loanApplication.setApplicantName(applicantName);
		loanApplication.setApplicantEmailId(applicantEmailId);
		loanApplication.setAmount(loanAmount);
		loanApplication.setStatus(LoanStatus.PENDING);

		LoanApplicationDto createdLoanApplication = loanApplicationService.saveLoanApplication(loanApplication);
		execution.setVariable("loanApplicationId", createdLoanApplication.getId());
		execution.setProcessBusinessKey(createdLoanApplication.getId());
	}

	/**
	 * Update the status of the application
	 * @param execution
	 */
	public void updateLoanApplicationStatus(DelegateExecution execution) {
		String loanStatus = (String) execution.getVariable("loanStatus");
		LoanStatus newStatus = LoanStatus.valueOf(loanStatus);
		String statusReason = (String) execution.getVariable("loanStatusReason");

		String loanApplicationId = (String) execution.getVariable("loanApplicationId");
		LoanApplicationDto loanApplication = loanApplicationService.fetchLoanApplication(loanApplicationId);
		loanApplication.setStatus(newStatus);
		loanApplication.setStatusReason(statusReason);

		loanApplicationService.saveLoanApplication(loanApplication);
	}
}
