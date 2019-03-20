package co.gov.metropol.area247.jdbc.dao.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DaoConfig {

	// @Autowired
	// private DataSource dataSource;

	/*
	 * @Bean public PlatformTransactionManager txManager() { return new
	 * DataSourceTransactionManager(dataSource); }
	 */

}
