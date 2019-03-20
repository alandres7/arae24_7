package co.gov.metropol.area247.entorno.repository.impl;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.entorno.domain.Agua;
import co.gov.metropol.area247.entorno.model.RecomendacionAire;
import co.gov.metropol.area247.entorno.repository.EstacionRepository;
import co.gov.metropol.area247.entorno.service.IEntornoRecomendacionAireService;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
public class EstacionRepositoryImpl extends Dao implements EstacionRepository {
	
	@Autowired
	IEntornoRecomendacionAireService recomenAireService;

	private static Logger LOGGER = LoggerFactory.getLogger(EstacionRepositoryImpl.class);

	private static String SQL = "";

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	@Value("${iconos.server.url}")
	private String urlIconos;

	@Override
	public List<Agua> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agua buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDetailXMarker(Long idMarcador) throws SQLException {
		try {
			SQL = "SELECT" + " MAR.ID AS ID," + " MAR.S_NOMBRE AS NOMBRE," + " EST.S_MUNICIPIO AS MUNICIPIO,"
					+ " MAR.S_DESCRIPCION AS DESCRIPCION," + " MAR.S_COLOR_FONDO AS ESTADO, " + " ICO.ID AS ICONO, "
					+ " EST.SUBCUENCA, EST.S_RECOMENDACIONES AS RECOMENDACIONES, "
					+ " CAP.D_ULTIMA_ACTUALIZACION AS ULTIMA_ACTUALIZACION, "
					+ " EST.ID AS ID_ESTACION, " 
					+"MED.S_DESCRIPCION DESCRIPCION_AIRE," 
					+"EST.S_CATEGORIA DESCRIPCION_AGUA, MED.S_SIGNIFICADO INTERPRETACION "
					+ " FROM CONTENEDOR.D247CON_ICONO ICO, CONTENEDOR.D247CON_MARCADOR MAR"
					+ " INNER JOIN CONTENEDOR.D247ENT_ESTACION EST ON EST.ID_MARCADOR = MAR.ID"
					+ " INNER JOIN CONTENEDOR.D247CON_CAPA CAP ON CAP.ID = MAR.ID_CAPA "
					+ "LEFT JOIN CONTENEDOR.T247ENT_MEDICION MED ON MED.S_COLOR = EST.S_COLOR_HEX "
					+ " WHERE ICO.ID = MAR.ID_ICONO AND MAR.ID = ?";

			return getJdbcTemplate().queryForObject(SQL, new Object[] {idMarcador},
					(RowMapper<String>) (rs, rowNum) -> {
						return cargarData(rs);
					});
		} catch (Exception e) {
			LOGGER.error("Error al obtener el (Aire) (Agua) por capa --{}{}", idMarcador, e);
			throw new SQLException("Error al obtener el (Aire) (Agua) por capa --{}{}", e);
		}
	}

	public String cargarData(ResultSet rs) {				
		try {
			return "[{\"nombre\":\"" + rs.getString("NOMBRE") + "\"}" + ",{\"municipio\":\""
					+ rs.getString("MUNICIPIO") + "\"}," + "{\"estado\":\"" + rs.getString("ESTADO") + "\"}," 
					+"{\"subcuenca\":\""+getSubcuenca(rs.getString("SUBCUENCA")) + "\"},"
					+ "{\"icono\":\""+ urlIconos+rs.getString("ICONO") + "\"},"
					+ "{\"descripcionAire\":\""+rs.getString("DESCRIPCION_AIRE")+ "\"}," 
					+ "{\"descripcionAgua\":\""+rs.getString("DESCRIPCION_AGUA")+ "\"},"
					+ "{\"interpretacion\":\""+rs.getString("INTERPRETACION")+ "\"}," 
					+ "{\"recomendaciones\":\"" + transformRecomendaciones(rs.getString("RECOMENDACIONES")) + "\"}," 
					+ "{\"fecha\":\"" + DATE_TIME_FORMAT.format(rs.getDate("ULTIMA_ACTUALIZACION")) + "\"},"
					+ "{\"hora\":\"" + HOUR_FORMAT.format(rs.getDate("ULTIMA_ACTUALIZACION")) + "\"}," 
					+ "{\"ListaRecomendaciones\":" + generarJsonRecomendaciones(rs.getLong("ID_ESTACION")) + "}" 
					+ "]";
		} catch (Exception e) {
			LOGGER.error("Error al cargar el objeto agua --{}{}", e);
			throw new SQLException("Error al cargar el objeto agua --{}{}");
		}
	}
	
	public String generarJsonRecomendaciones(Long isEstacion) {				
		try {
			List<RecomendacionAire> listRecomen = recomenAireService.recomendacionesPorIdEstacion(isEstacion);			
			String jsonRecomen = "[";
			int indice = 0;
			for (RecomendacionAire recomen : listRecomen) {	
				String urlIcono = "";
				if(recomen.getIcono()!=null) {
					urlIcono = recomen.getIcono().getRutaLogo();
				}				
				jsonRecomen = jsonRecomen + "{\"texto\":\"" + recomen.getTexto() + "\",\"urlIcono\":\"" + urlIcono + "\"}";
				
				indice = indice + 1;				
				if(indice<listRecomen.size()) {    jsonRecomen = jsonRecomen + ",";    }				
			}
			jsonRecomen = jsonRecomen + "]";
			return jsonRecomen;
		} catch (Exception e) {
			LOGGER.error("Error al crear el json de recomendaciones", e);
			throw new SQLException("Error al crear el json de recomendaciones");
		}
	}
	
	@Override
	public Long getDetailNearestMarker(Long idCapa, double lat, double lng) {
		try {
			SQL = "SELECT ID_MARCADOR FROM " + 
					"(SELECT ID_MARCADOR, DISTANCE FROM " + 
					"(SELECT ID_MARCADOR," + 
					"SDO_GEOM.SDO_DISTANCE(" + 
					"    MDSYS.SDO_GEOMETRY(2001,8307,MDSYS.SDO_POINT_TYPE(:LNG ,:LAT,NULL),NULL,NULL)," + 
					"    COORDINATES,0.0001,'unit=KM') DISTANCE FROM " + 
					"(SELECT M.ID ID_MARCADOR, M.CLB_COORDENADA_PUNTO COORDINATES " + 
					"FROM D247CON_MARCADOR M " + 
					"INNER JOIN D247CON_CAPA C on M.ID_CAPA = C.ID " + 
					"WHERE  C.ID = :ID_CAPA) AIRE) MARKERS " + 
					"ORDER BY DISTANCE) ORDER_MARKERS " + 
					"WHERE ROWNUM <= 1";
			Map<String, Object> params = new HashMap<>();
			params.put(":LNG", lng);
			params.put(":LAT", lat);
			params.put(":ID_CAPA", idCapa);
			return getJdbcTemplate().queryForObject(SQL(SQL, params), 
											(RowMapper<Long>) (rs, rowNum) -> 
												{
											return rs.getLong("ID_MARCADOR");		
												});
		} catch (Exception e) {
			LOGGER.error("Error obteniendo marcador ",e);
			return 0L;
		}
	}
	
	private String transformRecomendaciones(String recomendaciones) {
		if(recomendaciones == null) {
			return "";
		}else {
			return recomendaciones
						.replace('[', ' ')
						.replace(']', ' ')
						.trim();
		}
	}
	
	
	
	private String getSubcuenca(String subcuenca) {
		if(subcuenca == null) {
			return "";
		}else {
			return subcuenca.trim();
		}
	}
	
	

}
