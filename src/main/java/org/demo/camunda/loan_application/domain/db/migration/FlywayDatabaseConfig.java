package org.demo.camunda.loan_application.domain.db.migration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayDatabaseConfig {

	@Bean
	public Flyway loanFlyway(@FlywayDataSource DataSource loanDataSource) {
		Flyway flyway = Flyway.configure()
							  .dataSource(loanDataSource)
							  .locations("classpath:loanapplicationdb/migration")
							  .baselineOnMigrate(true)
							  .load();

		flyway.migrate();
		return flyway;
	}
}
