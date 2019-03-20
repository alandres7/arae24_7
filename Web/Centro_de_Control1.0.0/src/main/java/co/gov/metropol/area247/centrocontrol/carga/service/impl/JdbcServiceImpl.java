package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.service.IJdbcService;

@Service
public class JdbcServiceImpl implements IJdbcService {

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String springDatasourceDriver;

	@Value("${spring.datasource.username}")
	private String springDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;

	@Override
	public Connection open() {
		Connection dbConnection = null;
		try {
			Class.forName(springDatasourceDriver);
			dbConnection = DriverManager.getConnection(springDatasourceUrl, springDatasourceUsername,
					springDatasourcePassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConnection;
	}

	@Override
	public void close(PreparedStatement ps, Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
		close(ps, conn);
	}
	
}
