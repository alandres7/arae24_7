package co.gov.metropol.area247.huellas.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * @author ageo.fuentes@ada.co
 * @version 1.0.0
 * @category Spring Boot App Module
 * */
@EntityScan(basePackages="co.gov.metropol.area247.huellas.entity")
@ComponentScan(basePackages = {
		"co.gov.metropol.area247.huellas.dao",
		"co.gov.metropol.area247.huellas.jdbc.dao",
		"co.gov.metropol.area247.huellas.mapper",
		"co.gov.metropol.area247.huellas.svc",
		"co.gov.metropol.area247.huellas.rest"
	})
	@EnableJpaRepositories(basePackages = "co.gov.metropol.area247.huellas.dao")
@SpringBootApplication
public class Area247HuellasApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(Area247HuellasApplication.class, args);
//	}
}