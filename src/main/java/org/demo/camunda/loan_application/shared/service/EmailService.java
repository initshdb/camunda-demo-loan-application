package org.demo.camunda.loan_application.shared.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String recipientEmailId, String message, String subject) {
        LOG.info("Sending email...\nSubject: {}\nRecipient: {}\nMessage: {}", subject, recipientEmailId, message);
    }
}
