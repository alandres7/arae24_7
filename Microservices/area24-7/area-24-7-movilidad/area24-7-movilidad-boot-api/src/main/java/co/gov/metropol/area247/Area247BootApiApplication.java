package co.gov.metropol.area247;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = { "co.gov.metropol.area247.repository.domain" })
@ComponentScan(basePackages = { "co.gov.metropol.area247.config", "co.gov.metropol.area247.rest",
		"co.gov.metropol.area247.web.task", "co.gov.metropol.area247.model.mapper",
		"co.gov.metropol.area247.repository", "co.gov.metropol.area247.repository.domain",
		"co.gov.metropol.area247.security.support.audit", "co.gov.metropol.area247.batch",
		"co.gov.metropol.area247.gateway", "co.gov.metropol.area247.service" })
@EnableJpaRepositories(basePackages = { "co.gov.metropol.area247.repository" })
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Area247BootApiApplication {

	// public static void main(String[] args) {
	// SpringApplication.run(Area247BootApiApplication.class, args);
	// }

}
