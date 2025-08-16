package org.demo.camunda.loan_application.web.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;
import java.util.Optional;

@Configuration
public class SpaConfig implements WebMvcConfigurer {

	private final WebProperties webProperties;

	public SpaConfig(WebProperties webProperties) {
		this.webProperties = webProperties;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations(webProperties.getResources().getStaticLocations())
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
					@Override protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
																		 List<? extends Resource> locations,
																		 ResourceResolverChain chain) {
						if (requestPath.startsWith("api") || requestPath.startsWith("camunda")) {
							return null;
						}

						Resource resource = super.resolveResourceInternal(request, requestPath, locations, chain);

						return Optional.ofNullable(resource).orElse(new ClassPathResource("public/loan-app/index.html"));
					}
				});
	}
}
