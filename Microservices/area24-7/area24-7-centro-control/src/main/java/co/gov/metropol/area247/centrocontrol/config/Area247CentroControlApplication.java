package co.gov.metropol.area247.centrocontrol.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "co.gov.metropol.area247.centrocontrol.model")
@ComponentScan(basePackages = {
	"co.gov.metropol.area247.centrocontrol.dao",	
	"co.gov.metropol.area247.centrocontrol.service",
	"co.gov.metropol.area247.centrocontrol.restcontroller"
})
@EnableJpaRepositories(basePackages = "co.gov.metropol.area247.centrocontrol.dao")
@SpringBootApplication
public class Area247CentroControlApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(Area247CentroControlApplication.class, args);
//	}
}
