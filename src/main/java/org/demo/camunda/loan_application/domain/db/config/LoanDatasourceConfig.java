package org.demo.camunda.loan_application.domain.db.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.demo.camunda.loan_application.domain.db.repository",  // repo package
					   entityManagerFactoryRef = "loanApplicationEntityManagerFactory",
					   transactionManagerRef = "loanApplicationTransactionManager")
public class LoanDatasourceConfig {


	@Bean(defaultCandidate = false, name = "loanApplicationDataSourceProperties")
	@ConfigurationProperties(prefix = "org.demo.camunda.loan-application.datasource")
	public DataSourceProperties loanApplicationDataSourceProperties() {
		return new DataSourceProperties();
	}

	@FlywayDataSource
	@Bean(defaultCandidate = false, name = "loanApplicationDataSource")
	public DataSource loanApplicationDataSource(
			@Qualifier(value = "loanApplicationDataSourceProperties") DataSourceProperties loanDataSourceProperties) {
		return loanDataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean(defaultCandidate = false, name = "loanApplicationEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean loanApplicationEntityManagerFactory(
			EntityManagerFactoryBuilder builder, @Qualifier(value = "loanApplicationDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("org.demo.camunda.loan_application.domain.db.entity").build();
	}

	@Bean(defaultCandidate = false, name = "loanApplicationTransactionManager")
	public JpaTransactionManager loanApplicationTransactionManager(
			@Qualifier(value = "loanApplicationEntityManagerFactory")
			LocalContainerEntityManagerFactoryBean loanEntityManagerFactory) {
		return new JpaTransactionManager(Objects.requireNonNull(loanEntityManagerFactory.getObject()));
	}
}
