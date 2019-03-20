package co.gov.metropol.area247.vigias.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "co.gov.metropol.area247.vigias.model")
@ComponentScan(basePackages = {
		"co.gov.metropol.area247.vigias.dao",
		"co.gov.metropol.area247.vigias.service",
		"co.gov.metropol.area247.vigias.restcontroller"
})
@EnableJpaRepositories(basePackages = "co.gov.metropol.area247.vigias.dao")
@SpringBootApplication
public class Area247VigiasApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(Area247VigiasApplication.class, args);
//	}
	
}