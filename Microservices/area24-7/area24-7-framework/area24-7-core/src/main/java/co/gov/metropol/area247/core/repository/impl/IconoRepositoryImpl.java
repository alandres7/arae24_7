package co.gov.metropol.area247.core.repository.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.core.domain.Icono;
import co.gov.metropol.area247.core.repository.IconoRepository;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
@Scope("prototype")
@Transactional(readOnly = true)
public class IconoRepositoryImpl extends Dao implements IconoRepository {
	
	
	private static String sql = "";
	
	private static final String EMPTY = " ";
	
	private static final String DATE_TIME_FORMAT_QUERY = "'dd/MM/YYYY HH24:MI:SS'";
	
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	private static final String FIRST_SECOND = "'00:00:01'";
	
	@Override
	public Long getIconoClimaActual(long idMarcador) {
		LocalDateTime now = LocalDateTime.now();
		sql = "SELECT P.ID_ICONO  FROM T247ENT_PRONOSTICO P " + 
				"INNER JOIN D247ENT_ESTACION E ON E.ID = P.ID_ESTACION " + 
				"INNER JOIN D247CON_MARCADOR M ON M.ID = E.ID_MARCADOR " + 
				"WHERE M.ID = :ID_MARCADOR " + 
				"AND (" + 
				" (TO_DATE('"+ 
				now.format(dateTimeFormat)+ 
				"',"+ DATE_TIME_FORMAT_QUERY+ ") " +
				" between p.D_TIEMPO_INICIAL and p.D_TIEMPO_FINAL) "
				+ "OR (P.D_TIEMPO_INICIAL < TO_DATE('"+ 
				now.format(dateTimeFormat)+ "',"+ 
				DATE_TIME_FORMAT_QUERY+ ") " + 
				" and p.D_TIEMPO_FINAL < TO_DATE('"+ 
				now.format(dateFormat)+EMPTY+ "' || "+
				FIRST_SECOND+ ","+DATE_TIME_FORMAT_QUERY+"))) AND ROWNUM <= 1 ";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put(":ID_MARCADOR", idMarcador);
			
			return getJdbcTemplate().queryForObject(SQL(sql, params),
					(RowMapper<Long>) (rs, rowNum) -> {
						return rs.getLong("ID_ICONO");
					});
			
		} catch (Exception e) {
		}
		return 0L;
	}

	@Override
	public List<Icono> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icono buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
