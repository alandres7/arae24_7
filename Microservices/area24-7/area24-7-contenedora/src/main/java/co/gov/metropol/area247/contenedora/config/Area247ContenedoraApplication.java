package co.gov.metropol.area247.contenedora.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EntityScan(basePackages = {"co.gov.metropol.area247.contenedora.model"})
@ComponentScan(basePackages = {
		"co.gov.metropol.area247.contenedora.model",
		"co.gov.metropol.area247.contenedora.mapper",
		"co.gov.metropol.area247.seguridad.mapper",
		"co.gov.metropol.area247.contenedora.dao",
		"co.gov.metropol.area247.contenedora.service",
		"co.gov.metropol.area247.contenedora.restcontroller",
		"co.gov.metropol.area247.contenedora.rest",
		"co.gov.metropol.area247.contenedora.util",
		"co.gov.metropol.area247.contenedora.gateway",
		"co.gov.metropol.area247.contenedora.config",
		"co.gov.metropol.area247.jdbc", 
		"co.gov.metropol.area247.core",
})
@EnableJpaRepositories(basePackages = {"co.gov.metropol.area247.contenedora.dao"})
@SpringBootApplication
public class Area247ContenedoraApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(Area247ContenedoraApplication.class, args);
//	}
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
	    return new HibernateJpaSessionFactoryBean();
	}
	
}
