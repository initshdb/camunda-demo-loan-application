package org.demo.camunda.loan_application.camunda.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.IdentityLink;
import org.demo.camunda.loan_application.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Sends notification via Service Task or Task Listeners
 */
@Service(value = "notificationService")
public class NotificationService implements JavaDelegate, TaskListener {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

	private final IdentityService identityService;
	private final EmailService emailService;

	public NotificationService(IdentityService identityService, EmailService emailService) {
		this.identityService = identityService;
		this.emailService = emailService;
	}

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String message = (String) delegateExecution.getVariableLocal("notificationMessage");
		String recipient = (String) delegateExecution.getVariableLocal("recipient");
		String subject = (String) delegateExecution.getVariableLocal("notificationSubject");


		User user = identityService.createUserQuery().userId(recipient).singleResult();

		// If the user does not exist, we should throw an exception. But for this demo, we have not registered the applicant in Camunda
		String recipientEmailId = Optional.ofNullable(user).map(User::getEmail).orElse(recipient);

		emailService.sendEmail(recipientEmailId, message, subject);
	}

	@Override
	public void notify(DelegateTask delegateTask) {
		String message = (String) delegateTask.getVariable("notificationMessage");
		String subject = (String) delegateTask.getVariable("notificationSubject");

		String assignedUserId = delegateTask.getCandidates()
											.stream()
											.map(IdentityLink::getUserId)
											.findFirst()
											.orElseThrow(() -> new RuntimeException("No Assigned User"));

		User user = identityService.createUserQuery().userId(assignedUserId).singleResult();

		String recipientEmailId = Optional.ofNullable(user)
										  .map(User::getEmail)
										  .orElseThrow(() -> new RuntimeException(String.format("Invalid User: %s",
																								assignedUserId)));

		emailService.sendEmail(recipientEmailId, message, subject);
	}
}
