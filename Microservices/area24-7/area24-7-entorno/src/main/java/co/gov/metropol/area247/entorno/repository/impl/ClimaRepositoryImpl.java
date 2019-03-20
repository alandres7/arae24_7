package co.gov.metropol.area247.entorno.repository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.entorno.domain.Clima;
import co.gov.metropol.area247.entorno.model.dto.PronosticoDetail;
import co.gov.metropol.area247.entorno.repository.ClimaRepository;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
public class ClimaRepositoryImpl extends Dao implements ClimaRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(ClimaRepositoryImpl.class);

	private static String SQL = "";
	
	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Long ID_CLIMA = 12L;
	
	@Value("${iconos.server.url}")
	private String urlIconos;

	@Override
	public List<Clima> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clima buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clima obtenerClimaPorIdCapaOMarcador(Long idCapa, Long idMarcador) throws SQLException {
		try {
			SQL = "SELECT" + " MAR.ID AS ID," + " MAR.S_NOMBRE AS NOMBRE," + " MAR.S_MUNICIPIO AS MUNICIPIO,"
					+ " MAR.S_DESCRIPCION AS DESCRIPCION," + " MAR.N_ACTIVO AS ESTADO, " + " ICO.S_RUTA_LOGO AS ICONO"
					+ " FROM CONTENEDOR.D247CON_MARCADOR MAR, CONTENEDOR.D247CON_ICONO ICO"
					+ " WHERE ICO.ID = MAR.ID_ICONO AND MAR.ID_CAPA =:ID_CAPA AND MAR.ID =:ID_MARCADOR";
			Map<String, Object> params = new HashMap<>();
			params.put(":ID_CAPA", idCapa);
			params.put(":ID_MARCADOR", idMarcador);
			return getJdbcTemplate().queryForObject(SQL(SQL, params), (RowMapper<Clima>) (rs, rowNum) -> {
				Clima clima =  new Clima();
				
				clima.addValue("id", rs.getString("ID"));
				clima.addValue("nombre", rs.getString("NOMBRE"));
				clima.addValue("municipio", rs.getString("MUNICIPIO"));
				clima.addValue("descripcion", rs.getString("DESCRIPCION"));
				clima.addValue("estado", rs.getString("ESTADO"));
				clima.addValue("icono", rs.getString("ICONO"));
				//clima.setId(rs.getString("ID"));
				//clima.setNombre( rs.getString("NOMBRE"));
				//clima.setMunicipio(rs.getString("MUNICIPIO"));
				//clima.setDescripcion(rs.getString("DESCRIPCION"));
				//clima.setEstado(rs.getString("ESTADO"));
				//clima.setIcono(rs.getString("ICONO"));
				return clima;
			});
		} catch (Exception e) {
			LOGGER.error("Error al obtener el clima por id capa e id marcador --{}{}", (idCapa + idMarcador), e);
			throw new SQLException("Error al obtener el clima por id capa e id marcador --{}{}", e);
		}
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PronosticoDetail> getDetailClimaXMarker(Long  idMarcador) {
		try{
			SQL = "SELECT TV.N_CODIGO_VENTANA CODIGO_VENTANA, TV.S_NOMBRE  TIEMPO, P.S_DESCRIPCION DESCRIPCION, "
					+ "P.ID_ICONO, P.D_TIEMPO_INICIAL TIEMPO_INICIAL, P.D_TIEMPO_FINAL TIEMPO_FINAL, " 
					+ "E.S_RANGO_TEMPERATURA TEMPERATURA, "					
                    + "E.S_TEMPERATURA_MINIMA TEMPERATURA_MINIMA, "
                    + "E.S_TEMPERATURA_MAXIMA TEMPERATURA_MAXIMA, "					
					+ "E.S_MUNICIPIO MUNICIPIO,"
					+ " E.S_NOMBRE NOMBRE_ESTACION " 
					+ "FROM T247ENT_PRONOSTICO P " 
					+ "INNER JOIN D247ENT_ESTACION E ON P.ID_ESTACION = E.ID " 
					+ "INNER JOIN D247CON_MARCADOR M ON M.ID = E.ID_MARCADOR " 
					+ "INNER JOIN D247ENT_TIPO_VENTANA TV ON TV.ID = P.ID_TIPO_VENTANA "
					+ "WHERE M.ID = :ID_MARCADOR";
			Map<String, Object> params = new HashMap<>();
			params.put("ID_MARCADOR", idMarcador);
			return getNamedParameterJdbcTemplate().query(SQL, params, 
					(RowMapper<PronosticoDetail>) (rs, rowNum) -> 
												{
													try {
														return new PronosticoDetail(
																DATE_TIME_FORMAT.parse(DATE_TIME_FORMAT.format(rs.getDate("TIEMPO_INICIAL"))),
																DATE_TIME_FORMAT.parse(DATE_TIME_FORMAT.format(rs.getDate("TIEMPO_FINAL"))),
																rs.getLong("CODIGO_VENTANA"),
																rs.getString("TIEMPO"),
																rs.getString("DESCRIPCION"),
																urlIconos + rs.getString("ID_ICONO"),
																rs.getString("TEMPERATURA"),
																rs.getString("TEMPERATURA_MINIMA"),
																rs.getString("TEMPERATURA_MAXIMA"),
																rs.getString("MUNICIPIO"),
																rs.getString("NOMBRE_ESTACION")
																);
													} catch (ParseException e) {
													}
													return new PronosticoDetail();
												});
		} catch (Exception e) {
			LOGGER.error("Error al obtener el clima por id marcador --{}{}", (idMarcador), e);
			throw new SQLException("Error al obtener el clima por id marcador --{}{}", e);
		}
		
	}
	
	@Override
	public Long getContainsMarker(double lat, double lng) {
		try {
			SQL = "SELECT ID_MARCADOR FROM " 
					+"(SELECT ID_MARCADOR, "
					+ "SDO_GEOM.RELATE(POLYGON,'determine', MDSYS.SDO_GEOMETRY(2001, 8307," 
					+" MDSYS.SDO_POINT_TYPE(:LNG, :LAT, NULL), NULL, NULL), 0.005) RELATIONSHIP " 
					+" FROM " + 
					"(SELECT  M.ID ID_MARCADOR, M.CLB_COORDENADA_POLYGON POLYGON " + 
					"FROM D247CON_MARCADOR M " + 
					"INNER JOIN D247CON_CAPA C ON M.ID_CAPA = C.ID " + 
					"WHERE  C.ID = :ID_CAPA ) CLIMA) RELATIONSHIPS " + 
					"WHERE RELATIONSHIP <> 'DISJOINT'";
			Map<String, Object> params = new HashMap<>();
			params.put("LNG", lng);
			params.put("LAT", lat);
			params.put("ID_CAPA", ID_CLIMA);
			return getNamedParameterJdbcTemplate().query(SQL, params, 
					(RowMapper<Long>) (rs, rowNum) -> 
												{
											return rs.getLong("ID_MARCADOR");		
												}).get(0);
		} catch (Exception e) {
			LOGGER.error("Error obteniendo marcador ",e);
			return 0L;
		}
	}
	

}
