package co.gov.metropol.area247.centrocontrol.carga.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IJdbcService {
	
	Connection open();
	
	void close(PreparedStatement ps, Connection conn);

	void close(ResultSet rs, PreparedStatement ps, Connection conn);
}
