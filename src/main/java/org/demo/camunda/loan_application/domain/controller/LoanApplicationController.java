package org.demo.camunda.loan_application.domain.controller;

import org.demo.camunda.loan_application.domain.model.LoanApplicationDto;
import org.demo.camunda.loan_application.domain.service.LoanApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan-app")
//@Path("/loan-app")
public class LoanApplicationController {

	private final LoanApplicationService loanApplicationService;

	public LoanApplicationController(LoanApplicationService loanApplicationService) {
		this.loanApplicationService = loanApplicationService;
	}

	@GetMapping("applications")
	public List<LoanApplicationDto> loanApplications() {
		return loanApplicationService.fetchLoanApplications();
	}
}
