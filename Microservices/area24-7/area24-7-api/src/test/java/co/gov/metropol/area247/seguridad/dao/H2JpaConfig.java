package co.gov.metropol.area247.seguridad.dao;

import javax.activation.DataSource;

//import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "co.gov.metropol.area247.seguridad.dao")
@PropertySource("classpath:persistence-generic-entity.properties")
@EnableTransactionManagement
public class H2JpaConfig {
	
	 @Autowired
	    private Environment env;
	     
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	        dataSource.setUrl(env.getProperty("jdbc.url"));
	        dataSource.setUsername(env.getProperty("jdbc.user"));
	        dataSource.setPassword(env.getProperty("jdbc.pass"));
	 
	        return (DataSource) dataSource;
	    }
	    
	    // configure entityManagerFactory
	     
	    // configure transactionManager
	 
	    // configure additional Hibernate Properties

}
