package co.gov.metropol.area247.huellas.jdbc.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.huellas.entity.Posconsumo;
import co.gov.metropol.area247.huellas.jdbc.dao.IHuellasPosconsumoDaoJDBC;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
public class IHuellasPosconsumoDaoJDBCImpl extends Dao implements IHuellasPosconsumoDaoJDBC {
	
	private static Logger LOGGER = LoggerFactory.getLogger(IHuellasPosconsumoDaoJDBCImpl.class);
	
	private static String sql;
	
	@Value("${iconos.server.url}")
	private String urlIconos;

	@Override
	public String getDetailXMarker(Long idMarcador) throws SQLException {
		try {
			sql = "SELECT PC.S_NOMBRE NOMBRE, PC.S_DIRECCION DIRECCION, PC.S_HORARIO HORARIO, "
					+ " ' ' TELEFONO, PC.S_EMAIL EMAIL,  "
					+ " PC.S_PROGRAMA || ' - '||PC.S_TIPO_RESIDUO ||' - '||PC.S_RESIDUO DESCRIPCION,  "
					+ "' - ' LICENCIA_AMBIENTAL,  C.ID_ICONO ICONO "
					+ " FROM D247HUE_POSCONSUMO PC "
					+ " INNER JOIN D247CON_MARCADOR M ON PC.ID_MARCADOR = M.ID "
					+ " INNER JOIN D247CON_CATEGORIA C ON M.ID_CATEGORIA = C.ID "
					+ " WHERE M.ID =  ?";

			return getJdbcTemplate().queryForObject(sql, new Object[] {idMarcador},
					(RowMapper<String>) (rs, rowNum) -> {
						return cargarData(rs);
					});
		} catch (Exception e) {
			LOGGER.error("Error al obtener el punto posconsumo por ID de Marcador --{}{}", idMarcador, e);
			throw new SQLException("Error al obtener el punto posconsumo por ID de Marcador --{}{}", e);
		}
	}
	
	public String cargarData(ResultSet rs) {
		try {
			return "[{\"Nombre\":\"" + rs.getString("NOMBRE") + "\"}" 
					+ ",{\"Dirección\":\""+ rs.getString("DIRECCION") + "\"}," 
					+ "{\"Horario\":\"" + rs.getString("HORARIO") + "\"}," 
					+"{\"Teléfono\":\""+rs.getString("TELEFONO") + "\"},"
					+ "{\"Email\":\"" +rs.getString("EMAIL")+ "\"},"
					+ "{\"Descripción\":\"" +rs.getString("DESCRIPCION")+ "\"},"
					+ "{\"Licencia Ambiental\":\"" +rs.getString("LICENCIA_AMBIENTAL")+ "\"},"
					+ "{\"icono\":\""+ urlIconos+rs.getString("ICONO") + "\"}" 
					+ "]";
		} catch (Exception e) {
			LOGGER.error("Error al cargar el punto posconsumo --{}{}", e);
			throw new SQLException("Error al cargar el punto posconsumopunto posconsumo --{}{}");
		}
	}
	
	@Override
	public List<Posconsumo> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Posconsumo buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
