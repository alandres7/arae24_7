package co.gov.metropol.area247.covid19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@EnableScheduling
@SpringBootApplication
public class ApiMicCovid19Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiMicCovid19Application.class, args);
	}

}
