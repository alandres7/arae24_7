package co.gov.metropol.area247.entorno.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//@Import(Area247ContenedoraApplication.class)
@EntityScan(basePackages = {"co.gov.metropol.area247.entorno.model"} )
@ComponentScan(basePackages = {
		"co.gov.metropol.area247.entorno.config",
		"co.gov.metropol.area247.entorno.dao",
		"co.gov.metropol.area247.entorno.gateway",
		"co.gov.metropol.area247.entorno.service",
		"co.gov.metropol.area247.entorno.repository",
		"co.gov.metropol.area247.entorno.restcontroller"
})
@EnableJpaRepositories(basePackages = {"co.gov.metropol.area247.entorno.dao"})
@SpringBootApplication
public class Area247EntornoApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(Area247EntornoApplication.class, args);
//	}
	
}
