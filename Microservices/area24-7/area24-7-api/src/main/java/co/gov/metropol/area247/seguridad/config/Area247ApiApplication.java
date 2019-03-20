package co.gov.metropol.area247.seguridad.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import co.gov.metropol.area247.Area247BootApiApplication;
import co.gov.metropol.area247.avistamiento.config.Area247AvistamientoApplication;
import co.gov.metropol.area247.centrocontrol.config.Area247CentroControlApplication;
import co.gov.metropol.area247.contenedora.config.Area247ContenedoraApplication;
import co.gov.metropol.area247.entorno.config.Area247EntornoApplication;
import co.gov.metropol.area247.huellas.config.Area247HuellasApplication;
import co.gov.metropol.area247.vigias.config.Area247VigiasApplication;

@Import({ Area247ContenedoraApplication.class, Area247EntornoApplication.class, Area247AvistamientoApplication.class,
		Area247CentroControlApplication.class, Area247VigiasApplication.class, Area247BootApiApplication.class,
		Area247HuellasApplication.class })
@EntityScan(basePackages = "co.gov.metropol.area247.seguridad.model")
@ComponentScan(basePackages = { "co.gov.metropol.area247.seguridad.restcontroller",
		"co.gov.metropol.area247.seguridad.service", "co.gov.metropol.area247.seguridad.dao",
		"co.gov.metropol.area247.seguridad.config" })
@EnableJpaRepositories(basePackages = "co.gov.metropol.area247.seguridad.dao")
@SpringBootApplication
public class Area247ApiApplication {

	private int maxUploadSizeInMb = 40 * 1024 * 1024; // 40 MB

	public static void main(String[] args) {
		SpringApplication.run(Area247ApiApplication.class, args);
	}

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
			if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
				// -1 means unlimited
				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
			}
		});

		return tomcat;

	}
	
}
