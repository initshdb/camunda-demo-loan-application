package org.demo.camunda.loan_application.domain.db.entity;

import jakarta.persistence.*;
import org.demo.camunda.loan_application.domain.model.LoanStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "LOAN_APPLICATION")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "applicant_name", nullable = false)
	private String applicantName;

	@Column(name = "applicant_email_id", nullable = false)
	private String applicantEmailId;

	@Column(nullable = false)
	private Double amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanStatus status;

	@Column(name = "status_reason")
	private String statusReason;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	public LoanApplication() {
	}

	public LoanApplication(String applicantName, Double amount, LoanStatus status) {
		this.applicantName = applicantName;
		this.amount = amount;
		this.status = status;
	}

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = createdAt;
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public String getApplicantEmailId() {
		return applicantEmailId;
	}

	public void setApplicantEmailId(String applicantEmailId) {
		this.applicantEmailId = applicantEmailId;
	}
}
