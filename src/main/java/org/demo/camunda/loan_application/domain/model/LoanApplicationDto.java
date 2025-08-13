package org.demo.camunda.loan_application.domain.model;

import jakarta.persistence.Column;
import org.demo.camunda.loan_application.domain.db.entity.LoanApplication;

public class LoanApplicationDto {
    private String id;

    private String applicantName;

    private String applicantEmailId;

    private Double amount;

    private LoanStatus status;

    private String statusReason;

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

    public static LoanApplication toEntity(LoanApplicationDto dto) {
        LoanApplication entity = new LoanApplication();
        entity.setId(dto.getId());
        entity.setApplicantName(dto.getApplicantName());
        entity.setApplicantEmailId(dto.getApplicantEmailId());
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
        entity.setStatusReason(dto.getStatusReason());

        return entity;
    }


    public static LoanApplicationDto fromEntity(LoanApplication entity) {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(entity.getId());
        dto.setApplicantName(entity.getApplicantName());
        dto.setApplicantEmailId(entity.getApplicantEmailId());
		dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    public String getApplicantEmailId() {
        return applicantEmailId;
    }

    public void setApplicantEmailId(String applicantEmailId) {
        this.applicantEmailId = applicantEmailId;
    }
}
