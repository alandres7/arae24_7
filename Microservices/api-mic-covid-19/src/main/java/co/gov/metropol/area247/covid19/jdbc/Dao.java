package co.gov.metropol.area247.covid19.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public abstract class Dao extends NamedParameterJdbcDaoSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Dao.class);

	@Autowired
	private DataSource dataSource;

	public Dao() {

	}

	@Override
	protected void checkDaoConfig() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("--- CheckConfigDao ---");
		}

		super.setDataSource(this.dataSource);
	}

	@Override
	protected void initDao() throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("--- initDao ---");
		}
		super.initDao();
	}

	/**
	 * Permite obtener el {@link String} del archivo SQL y reemplazar sentencias
	 * SQL
	 * 
	 * @param archivoSQL
	 * @param replacementQueryParams
	 * @return
	 * @throws SQLException
	 */
	protected String SQL(String sql, Map<String, Object> replacementQueryParams) throws SQLException {
		try {
			if (replacementQueryParams != null && !replacementQueryParams.isEmpty()) {
				for (Map.Entry<String, Object> entry : replacementQueryParams.entrySet()) {
					sql = sql.replaceAll(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error al reemplazar los valores en la SQL {} ", (replacementQueryParams), e);
			throw new SQLException("Error al reemplazar los valores en la SQL {} ");
		}

		return sql;
	}

	/**
	 * Obtiene el nombre de la secuencia.
	 * 
	 * @return
	 */
	protected abstract String getSequenceName();

	/**
	 * Set del nombre de la secuencia
	 * 
	 * @param sequenceName
	 * 
	 *            protected abstract void setSequenceName(String sequenceName);
	 */

	/**
	 * Permite obtener el ID del objeto de dominio
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected BigDecimal getDomainKeyValue() throws SQLException {
		return getNextSequenceValue(getSequenceName());
	}

	/**
	 * Permite onbtener el siuguiente valor de la secuencia
	 * 
	 * @param sequencieName
	 * @return
	 * @throws SQLException
	 */
	private BigDecimal getNextSequenceValue(String sequencieName) throws SQLException {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
			return jdbcTemplate.queryForObject("SELECT " + sequencieName + ".NEXTVAL AS NEXT_VALUE FROM DUAL",
					new RowMapper<BigDecimal>() {

						@Override
						public BigDecimal mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
							return rs.getBigDecimal("NEXT_VALUE");
						}
					});
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar la secuencia {}", sequencieName, e);
			throw new SQLException("Error al ejecutar la secuencia {}", e);
		}
	}

	/**
	 * Permite valdiar si una columna es valida en el {@link ResultSet}
	 */
	public static boolean hasColumnResultSet(ResultSet rs, String column) {
		try {

			int i = rs.findColumn(column);
			if ((i >= 0) && !Utils.isNull.apply(rs.getObject(column))) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
