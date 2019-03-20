package co.gov.metropol.area247.avistamiento.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@EntityScan(basePackages = "co.gov.metropol.area247.avistamiento.model")
@ComponentScan(basePackages = { "co.gov.metropol.area247.avistamiento.dao",
		"co.gov.metropol.area247.avistamiento.service", "co.gov.metropol.area247.avistamiento.repository",
		"co.gov.metropol.area247.avistamiento.restcontroller" })
@EnableJpaRepositories(basePackages = "co.gov.metropol.area247.avistamiento.dao")
@Configuration
@SpringBootApplication
public class Area247AvistamientoApplication {

	/*
	 * 
	 * public static void main(String[] args) {
	 * SpringApplication.run(Area247AvistamientoApplication.class, args); }
	 */

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		return builder.modulesToInstall(new JavaTimeModule());
	}

}
