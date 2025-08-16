package org.demo.camunda.loan_application.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.demo.camunda.loan_application.external.ExternalServiceRepository;
import org.demo.camunda.loan_application.external.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "externalServiceDelegate")
public class ExternalServiceDelegate implements JavaDelegate {

	private final ExternalServiceRepository externalServiceRepository;

	public ExternalServiceDelegate(ExternalServiceRepository externalServiceRepository) {
		this.externalServiceRepository = externalServiceRepository;
	}

	@Override public void execute(DelegateExecution execution) throws Exception {

		String productId = (String) execution.getVariable("productId");

		List<Product> products = externalServiceRepository.productsById(productId);

		Product product = products.getFirst();

		String name = product.getName();

		execution.setVariable("productName", name);

	}
}
