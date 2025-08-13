package org.demo.camunda.loan_application.domain.db.repository;

import org.demo.camunda.loan_application.domain.db.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, String> {
}
