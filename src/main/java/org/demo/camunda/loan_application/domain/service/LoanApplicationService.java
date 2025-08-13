package org.demo.camunda.loan_application.domain.service;

import org.demo.camunda.loan_application.domain.db.entity.LoanApplication;
import org.demo.camunda.loan_application.domain.db.repository.LoanApplicationRepository;
import org.demo.camunda.loan_application.domain.model.LoanApplicationDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Loan Application Service to handle CRUD operations
 */
@Service(value = "loanApplicationService")
public class LoanApplicationService {

	private final LoanApplicationRepository loanApplicationRepository;

	public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
		this.loanApplicationRepository = loanApplicationRepository;
	}

	public LoanApplicationDto saveLoanApplication(LoanApplicationDto loanApplicationDto) {
		LoanApplication entity = LoanApplicationDto.toEntity(loanApplicationDto);
		LoanApplication savedEntity = loanApplicationRepository.save(entity);

		return LoanApplicationDto.fromEntity(savedEntity);
	}

	public LoanApplicationDto fetchLoanApplication(String id) {
		Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(id);

		return loanApplication.map(LoanApplicationDto::fromEntity)
							  .orElseThrow(() -> new RuntimeException(String.format("Loan Application %s does not exist",
																					id)));
	}

}
