package co.gov.metropol.area247.avistamiento.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.avistamiento.dao.IAAvistamientoReporteJDBCRepository;
import co.gov.metropol.area247.avistamiento.dao.report.rowmapper.ReportRangoFechaRowMapper;
import co.gov.metropol.area247.avistamiento.model.dto.ReportDto;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
@Transactional(readOnly = true)
public class IAvistamientoReporteJDBCRepositoryImpl extends Dao implements IAAvistamientoReporteJDBCRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(IAvistamientoReporteJDBCRepositoryImpl.class);

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	private static final String INIT_DAY = " 00:00:00";

	private static final String END_DAY = " 23:59:59";

	private static String SQL = "";

	@Override
	public List<ReportDto> obtenerAvistamientosPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			                                                 String filtroCapa, String filtroCateg)
			throws SQLException {
		try {
			SQL = "SELECT USU.ID AS ID, USU.USERNAME AS NOMBRE, SUBQUERY.TOTAL AS TOTAL, " +
					"SUBQUERY.ESTADO_APROBADO AS ESTADO_APROBADO, " +
					"SUBQUERY.ESTADO_PENDIENTE AS ESTADO_PENDIENTE, " +
					"SUBQUERY.ESTADO_RECHAZADO AS ESTADO_RECHAZADO " +
					"FROM CONTENEDOR.T247SEG_USUARIO USU, " +
					"   (SELECT COUNT(CASE AVI.N_ESTADO WHEN 1 THEN 1 END) AS  ESTADO_APROBADO, " +
					"    COUNT(CASE AVI.N_ESTADO WHEN 2 THEN 1 END) AS  ESTADO_PENDIENTE, " +
					"    COUNT(CASE AVI.N_ESTADO WHEN 0 THEN 1 END) AS  ESTADO_RECHAZADO, " +
					"    COUNT(AVI.ID) AS TOTAL, AVI.ID_USUARIO AS ID_USUARIO " +
					"    FROM AVISTAM.T247AVI_AVISTAMIENTO AVI LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR " +
					"    ON (AVI.ID_MARCADOR = MAR.ID) " +
					"    WHERE :RANGO_FECHAS AND AVI.ID_USUARIO = ID_USUARIO " +
					"    AND ( (MAR.ID_CAPA IN ("+filtroCapa+") AND MAR.ID_CATEGORIA IS NULL) OR " +
					"          (MAR.ID_CATEGORIA IN ("+filtroCateg+") AND MAR.ID_CAPA IS NULL) ) " +
					"    GROUP BY  AVI.ID_USUARIO) SUBQUERY " +
					"WHERE SUBQUERY.ID_USUARIO = USU.ID";

			Map<String, Object> parametros = new HashMap<>();

			parametros.put(":RANGO_FECHAS",
					"AVI.D_PUBLICACION BETWEEN TO_DATE('" + fechaInicio.format(formatter) + INIT_DAY
							+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + fechaFin.format(formatter) + END_DAY
							+ "','dd/MM/YYYY HH24:MI:SS')");

			System.out.println(SQL(SQL, parametros));
			
			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), new ReportRangoFechaRowMapper());
		} catch (Exception e) {
			LOGGER.error("Error al consultar el reporte de por rango de fechas --{}{}", e);
			throw new SQLException("Error al consultar el reporte de por rango de fechas --{}{}", e);
		}

	}
	
	
	@Override
	public List<ReportDto> obtenerAvistamientosMunicipioPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin,
			                                                          String filtroCapa, String filtroCateg)
			throws SQLException {
		try {
			SQL = "SELECT 0 ID, MAR.S_MUNICIPIO AS NOMBRE, " + 
					"COUNT(CASE AVI.N_ESTADO WHEN 1 THEN 1 END) AS  ESTADO_APROBADO, " + 
					"COUNT(CASE AVI.N_ESTADO WHEN 2 THEN 1 END) AS  ESTADO_PENDIENTE, " + 
					"COUNT(CASE AVI.N_ESTADO WHEN 0 THEN 1 END) AS  ESTADO_RECHAZADO, " + 
					"COUNT(AVI.ID) AS TOTAL " + 
					"FROM AVISTAM.T247AVI_AVISTAMIENTO AVI " + 
					"JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) " + 
					"WHERE :RANGO_FECHAS AND MAR.S_MUNICIPIO IS NOT NULL " +
					"    AND ( (MAR.ID_CAPA IN ("+filtroCapa+") AND MAR.ID_CATEGORIA IS NULL) OR " +
					"          (MAR.ID_CATEGORIA IN ("+filtroCateg+") AND MAR.ID_CAPA IS NULL) ) " +
					"GROUP BY MAR.S_MUNICIPIO"; 

			Map<String, Object> parametros = new HashMap<>();

			parametros.put(":RANGO_FECHAS",
					"AVI.D_PUBLICACION BETWEEN TO_DATE('" + fechaInicio.format(formatter) + INIT_DAY
							+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + fechaFin.format(formatter) + END_DAY
							+ "','dd/MM/YYYY HH24:MI:SS')");

			System.out.println(SQL(SQL, parametros));
			
			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), new ReportRangoFechaRowMapper());
		} catch (Exception e) {
			LOGGER.error("Error al consultar el reporte de por rango de fechas --{}{}", e);
			throw new SQLException("Error al consultar el reporte de por rango de fechas --{}{}", e);
		}

	}
	
	
	@Override
	public Integer obtenerTotalAvistamientosPorRangoFecha(LocalDate fechaInicio,LocalDate fechaFin,
			                                              String filtroCapa,String filtroCateg,boolean soloPendientes)
			throws SQLException {
		try {
			SQL = "SELECT COUNT(*) AS TOTAL " + 
					"FROM AVISTAM.T247AVI_AVISTAMIENTO AVI " + 
					"JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) " + 
					"WHERE :RANGO_FECHAS :SOLOPENDIENTES " +
					"    AND ( (MAR.ID_CAPA IN ("+filtroCapa+") AND MAR.ID_CATEGORIA IS NULL) OR " +
					"          (MAR.ID_CATEGORIA IN ("+filtroCateg+") AND MAR.ID_CAPA IS NULL) ) "; 

			Map<String, Object> parametros = new HashMap<>();

			parametros.put(":RANGO_FECHAS",
					"AVI.D_PUBLICACION BETWEEN TO_DATE('" + fechaInicio.format(formatter) + INIT_DAY
							+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + fechaFin.format(formatter) + END_DAY
							+ "','dd/MM/YYYY HH24:MI:SS')");
			
			parametros.put(":SOLOPENDIENTES","");
			if (soloPendientes) {
				parametros.put(":SOLOPENDIENTES"," AND AVI.N_ESTADO = 2 ");
			}

			return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<Integer>) (rs, rowNum) -> {
				return rs.getInt("TOTAL");
			});			
		} catch (Exception e) {
			LOGGER.error("Error al consultar el reporte de por rango de fechas --{}{}", e);
			throw new SQLException("Error al consultar el reporte de por rango de fechas --{}{}", e);
		}

	}
	

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
