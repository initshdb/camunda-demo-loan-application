package org.demo.camunda.loan_application.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Configuration
public class ExternalServiceConfig {

	@Value("${org.camunda.demo.external-service-url}")
	private String externalServiceUrl;

	@Bean
	public ExternalServiceRepository externalServiceRepository() {
		RestClient rc = RestClient.builder()
								  .requestFactory(new HttpComponentsClientHttpRequestFactory())
								  .baseUrl(externalServiceUrl)
								  .defaultHeaders(httpHeaders -> httpHeaders.setContentType(
										  MediaType.APPLICATION_JSON))
								  .defaultHeaders(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
								  .build();

		RestClientAdapter adapter = RestClientAdapter.create(rc);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

		ExternalServiceRepository respository = factory.createClient(ExternalServiceRepository.class);
		return respository;
	}
}
