package org.demo.camunda.loan_application.external;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "/objects", contentType = MediaType.APPLICATION_JSON_VALUE,
			  accept = MediaType.APPLICATION_JSON_VALUE)
public interface ExternalServiceRepository {

	@GetExchange
	List<Product> productsById(@RequestParam("id") String id);
}
