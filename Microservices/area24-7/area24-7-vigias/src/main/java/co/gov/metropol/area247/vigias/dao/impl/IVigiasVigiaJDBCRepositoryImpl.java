package co.gov.metropol.area247.vigias.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.core.domain.en.TipoNivelCapa;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.domain.marker.dto.Point;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import co.gov.metropol.area247.jdbc.util.Utils;
import co.gov.metropol.area247.vigias.dao.IVigiasVigiaJDBCRepository;
import co.gov.metropol.area247.vigias.model.Vigia;
import co.gov.metropol.area247.vigias.model.dto.VigiaDto;


@Transactional
@Repository
public class IVigiasVigiaJDBCRepositoryImpl extends Dao implements IVigiasVigiaJDBCRepository {
	
	private static Logger LOGGER = LoggerFactory.getLogger(IVigiasVigiaJDBCRepositoryImpl.class);
	
	@Value("${media.server.url}")
	private String multimediaServerUrl;
	
	@Value("${iconos.server.url}")
	private String urlIconos;

	private static String SQL = "";

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	private static final String INIT_DAY = " 00:00:00";

	private static final String END_DAY = " 23:59:59";	
	
	
	@Override
	public List<VigiaDto> vigiaDtoObtenerTodos() throws SQLException {
		try {
			SQL = "SELECT VIG.ID AS ID_VIGIA, VIG.D_FECHA_REPORTE AS FECHA_REPORTE, " + 
				    "VIG.S_DESCRIPCION AS DESCRIPCION, VIG.S_DIRECCION AS DIRECCION, " + 
					"VIG.S_ESTADO_REPORTE AS ESTADO, VIG.S_ACTIVO AS ACTIVO, " + 
					"VIG.S_RADICADO_SIM AS RADICADO_SIM, VIG.ID_USUARIO AS ID_USUARIO, " + 
					"USU.USERNAME AS USERNAME, VIG.ID_NODO_ARBOL AS ID_NODO_ARBOL, " + 
					"VIG.S_RECORRIDO_ARBOL AS RECORRIDO_ARBOL, MAR.ID_ICONO AS ID_ICONO, " + 
					"VIG.ID_MULTIMEDIA AS ID_MULTIMEDIA, " +
					"VIG.ID_ICONO_VENTANA AS ID_ICONO_VENTANA, " +
					"VIG.ID_MARCADOR AS ID_MARCADOR " + 
					"FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG " + 
					"LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (VIG.ID_USUARIO = USU.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (VIG.ID_MARCADOR = MAR.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) ORDER BY VIG.ID DESC ";
			
			return getNamedParameterJdbcTemplate().query(SQL, (RowMapper<VigiaDto>) (rs, rowNum) -> {
				VigiaDto vigia = new VigiaDto();
				vigia.setId(rs.getLong("ID_VIGIA"));
				vigia.setFechaReporte(rs.getTimestamp("FECHA_REPORTE"));
				vigia.setDescripcion(rs.getString("DESCRIPCION"));
				vigia.setDireccion(rs.getString("DIRECCION"));
				vigia.setEstado(rs.getString("ESTADO"));
				vigia.setActivo(rs.getInt("ACTIVO"));
				vigia.setRadicadoSim(rs.getString("RADICADO_SIM"));
				vigia.setIdUsuario(rs.getLong("ID_USUARIO"));
				vigia.setUsername(rs.getString("USERNAME"));
				vigia.setIdNodoArbol(rs.getLong("ID_NODO_ARBOL"));
				vigia.setRecorridoArbol(rs.getString("RECORRIDO_ARBOL"));
				vigia.setIdIcono(rs.getLong("ID_ICONO"));
				vigia.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				vigia.setIdIconoVentana(rs.getLong("ID_ICONO_VENTANA"));
				vigia.setIdMarcador(rs.getLong("ID_MARCADOR"));
				return vigia;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			return null;
		}
	}
	
	
	
	@Override
	public VigiaDto vigiaDtoConsultarPorIdVigiaOIdMarcador(Long idVigia, Long idMarcador) throws SQLException {
		try {
			SQL = "SELECT VIG.ID AS ID_VIGIA, VIG.D_FECHA_REPORTE AS FECHA_REPORTE, " + 
				    "VIG.S_DESCRIPCION AS DESCRIPCION, VIG.S_DIRECCION AS DIRECCION, " + 
					"VIG.S_ESTADO_REPORTE AS ESTADO, VIG.S_ACTIVO AS ACTIVO, " + 
					"VIG.S_RADICADO_SIM AS RADICADO_SIM, VIG.ID_USUARIO AS ID_USUARIO, " + 
					"USU.USERNAME AS USERNAME, VIG.ID_NODO_ARBOL AS ID_NODO_ARBOL, " + 
					"VIG.S_RECORRIDO_ARBOL AS RECORRIDO_ARBOL, MAR.ID_ICONO AS ID_ICONO, " + 
					"VIG.ID_MULTIMEDIA AS ID_MULTIMEDIA, " +
					"VIG.ID_ICONO_VENTANA AS ID_ICONO_VENTANA, " +
					"VIG.ID_MARCADOR AS ID_MARCADOR, " + 
					"(SELECT COR.X FROM CONTENEDOR.D247CON_MARCADOR M, " + 
					"TABLE(SDO_UTIL.GETVERTICES(M.CLB_COORDENADA_PUNTO)) COR WHERE M.ID = VIG.ID_MARCADOR) AS LONGITUD ," + 
					"(SELECT COR.Y FROM CONTENEDOR.D247CON_MARCADOR M, " + 
					"TABLE(SDO_UTIL.GETVERTICES(M.CLB_COORDENADA_PUNTO)) COR WHERE M.id = VIG.ID_MARCADOR) AS LATITUD " +  
					"FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG " + 
					"LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (VIG.ID_USUARIO = USU.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (VIG.ID_MARCADOR = MAR.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) " +
					"WHERE (1=1) :FILTER_VIGIA :FILTER_MARCADOR ";

			Map<String, Object> parametros = new HashMap<>();

			parametros.put(":FILTER_VIGIA", "");
			parametros.put(":FILTER_MARCADOR", "");

			if (!Utils.isZero(idVigia)) {
				parametros.put(":FILTER_VIGIA", " AND VIG.ID = " + idVigia);
			}

			if (!Utils.isZero(idMarcador)) {
				parametros.put(":FILTER_MARCADOR", " AND MAR.ID = " + idMarcador);
			}

			return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<VigiaDto>) (rs, rowNum) -> {
				VigiaDto vigia = new VigiaDto();
				vigia.setId(rs.getLong("ID_VIGIA"));
				vigia.setFechaReporte(rs.getTimestamp("FECHA_REPORTE"));
				vigia.setDescripcion(rs.getString("DESCRIPCION"));
				vigia.setDireccion(rs.getString("DIRECCION"));
				vigia.setEstado(rs.getString("ESTADO"));
				vigia.setActivo(rs.getInt("ACTIVO"));
				vigia.setRadicadoSim(rs.getString("RADICADO_SIM"));
				vigia.setIdUsuario(rs.getLong("ID_USUARIO"));
				vigia.setUsername(rs.getString("USERNAME"));
				vigia.setIdNodoArbol(rs.getLong("ID_NODO_ARBOL"));
				vigia.setRecorridoArbol(rs.getString("RECORRIDO_ARBOL"));
				vigia.setIdIcono(rs.getLong("ID_ICONO"));
				vigia.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				vigia.setIdIconoVentana(rs.getLong("ID_ICONO_VENTANA"));
				vigia.setIdMarcador(rs.getLong("ID_MARCADOR"));
				vigia.setLatitud(rs.getString("LATITUD"));
				vigia.setLongitud(rs.getString("LONGITUD"));
				return vigia;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los reportes de vigía {}{}--", e);
			return null;
			
		}			
	}
	
	
	
	@Override
	public List<VigiaDto> vigiaDtoPorIdUsuario(Long idUsuario) throws SQLException {
		try {
			SQL = "SELECT VIG.ID AS ID_VIGIA, VIG.D_FECHA_REPORTE AS FECHA_REPORTE, " + 
				    "VIG.S_DESCRIPCION AS DESCRIPCION, VIG.S_DIRECCION AS DIRECCION, " + 
					"VIG.S_ESTADO_REPORTE AS ESTADO, VIG.S_ACTIVO AS ACTIVO, " + 
					"VIG.S_RADICADO_SIM AS RADICADO_SIM, VIG.ID_USUARIO AS ID_USUARIO, " + 
					"USU.USERNAME AS USERNAME, VIG.ID_NODO_ARBOL AS ID_NODO_ARBOL, " + 
					"VIG.S_RECORRIDO_ARBOL AS RECORRIDO_ARBOL, MAR.ID_ICONO AS ID_ICONO, " + 
					"VIG.ID_MULTIMEDIA AS ID_MULTIMEDIA, " +
					"VIG.ID_ICONO_VENTANA AS ID_ICONO_VENTANA, " +
					"VIG.ID_MARCADOR AS ID_MARCADOR " + 
					"FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG " + 
					"LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (VIG.ID_USUARIO = USU.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (VIG.ID_MARCADOR = MAR.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) " +
					"WHERE VIG.ID_USUARIO = :ID_USUARIO";

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ID_USUARIO", idUsuario);
			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<VigiaDto>) (rs, rowNum) -> {
				VigiaDto vigia = new VigiaDto();
				vigia.setId(rs.getLong("ID_VIGIA"));
				vigia.setFechaReporte(rs.getTimestamp("FECHA_REPORTE"));
				vigia.setDescripcion(rs.getString("DESCRIPCION"));
				vigia.setDireccion(rs.getString("DIRECCION"));
				vigia.setEstado(rs.getString("ESTADO"));
				vigia.setActivo(rs.getInt("ACTIVO"));
				vigia.setRadicadoSim(rs.getString("RADICADO_SIM"));
				vigia.setIdUsuario(rs.getLong("ID_USUARIO"));
				vigia.setUsername(rs.getString("USERNAME"));
				vigia.setIdNodoArbol(rs.getLong("ID_NODO_ARBOL"));
				vigia.setRecorridoArbol(rs.getString("RECORRIDO_ARBOL"));
				vigia.setIdIcono(rs.getLong("ID_ICONO"));
				vigia.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				vigia.setIdIconoVentana(rs.getLong("ID_ICONO_VENTANA"));
				vigia.setIdMarcador(rs.getLong("ID_MARCADOR"));
				return vigia;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los reportes de vigía {}{}--", e);
			return null;
		}				
	}
	
	
	
	
	@Override
	public List<MarkerPoint> obtenerMarcadoresVigiaPorIdUsuarioOEstado(Long idUsuario, String estado) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			SQL = " SELECT MAR.ID, t.X AS X, t.Y AS Y, "
					+ " MAR.ID_ICONO AS RUTA_LOGO FROM CONTENEDOR.D247CON_MARCADOR MAR,"
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t,"
					+ "  VIGIAS.T247VIG_REPORTE_VIGIA VIG "
					+ "  WHERE VIG.ID_MARCADOR=MAR.ID ";

			parametros.put(":ID_USUARIO", idUsuario);
			
			if (idUsuario != null) {
    			SQL = SQL + " AND VIG.ID_USUARIO = :ID_USUARIO ";
    			parametros.put(":ID_USUARIO", idUsuario);
    		}
			
			if (estado != null) {
    			SQL = SQL + " AND VIG.S_ESTADO_REPORTE = ':ESTADO' ";
    			parametros.put(":ESTADO", estado);
    		}
			
			getJdbcTemplate().setFetchSize(1000);
			
			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), (rs, rowNum) ->
					new MarkerPoint(rs.getLong(1),
							new Point(rs.getDouble(3), rs.getDouble(2)),
							urlIconos+rs.getString(4)));
		} catch (Exception e) {
			LOGGER.error("Error al obtener los marcadores por usuario {}{}--", idUsuario, e);
			throw new SQLException("Error al obtener los marcadores por usuario {}{}--", e);
		}
	}
	
	
	
	
	@Override
	public List<VigiaDto> vigiaDtoPorEstado(String estado) throws SQLException {
		try {
			SQL = "SELECT VIG.ID AS ID_VIGIA, VIG.D_FECHA_REPORTE AS FECHA_REPORTE, " + 
				    "VIG.S_DESCRIPCION AS DESCRIPCION, VIG.S_DIRECCION AS DIRECCION, " + 
					"VIG.S_ESTADO_REPORTE AS ESTADO, VIG.S_ACTIVO AS ACTIVO, " + 
					"VIG.S_RADICADO_SIM AS RADICADO_SIM, VIG.ID_USUARIO AS ID_USUARIO, " + 
					"USU.USERNAME AS USERNAME, VIG.ID_NODO_ARBOL AS ID_NODO_ARBOL, " + 
					"VIG.S_RECORRIDO_ARBOL AS RECORRIDO_ARBOL, MAR.ID_ICONO AS ID_ICONO, " + 
					"VIG.ID_MULTIMEDIA AS ID_MULTIMEDIA, " +
					"VIG.ID_ICONO_VENTANA AS ID_ICONO_VENTANA, " +
					"VIG.ID_MARCADOR AS ID_MARCADOR " + 
					"FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG " + 
					"LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (VIG.ID_USUARIO = USU.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (VIG.ID_MARCADOR = MAR.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) " + 
					"LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) " +
					"WHERE VIG.S_ESTADO_REPORTE = :ESTADO ";

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ESTADO", estado);
			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<VigiaDto>) (rs, rowNum) -> {
				VigiaDto vigia = new VigiaDto();
				vigia.setId(rs.getLong("ID_VIGIA"));
				vigia.setFechaReporte(rs.getTimestamp("FECHA_REPORTE"));
				vigia.setDescripcion(rs.getString("DESCRIPCION"));
				vigia.setDireccion(rs.getString("DIRECCION"));
				vigia.setEstado(rs.getString("ESTADO"));
				vigia.setActivo(rs.getInt("ACTIVO"));
				vigia.setRadicadoSim(rs.getString("RADICADO_SIM"));
				vigia.setIdUsuario(rs.getLong("ID_USUARIO"));
				vigia.setUsername(rs.getString("USERNAME"));
				vigia.setIdNodoArbol(rs.getLong("ID_NODO_ARBOL"));
				vigia.setRecorridoArbol(rs.getString("RECORRIDO_ARBOL"));
				vigia.setIdIcono(rs.getLong("ID_ICONO"));
				vigia.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				vigia.setIdIconoVentana(rs.getLong("ID_ICONO_VENTANA"));
				vigia.setIdMarcador(rs.getLong("ID_MARCADOR"));
				return vigia;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los reportes de vigía {}{}--", e);
			return null;
		}
	}
	
	
	@Override
	public int obtenerCantidadVigiasPorParametros(String idCapa,String idCategoria, String estado, 
			LocalDate fechaInicio, LocalDate fechaFin,boolean comenPen) throws SQLException {
		try {

    		SQL = "SELECT COUNT(*) AS CANTIDAD FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG "
    				+ " INNER JOIN CONTENEDOR.D247CON_MARCADOR MAR ON MAR.ID = VIG.ID_MARCADOR "
    				+ " LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON CAT.ID = MAR.ID_CATEGORIA " 
    		        + " LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON CAP.ID = MAR.ID_CAPA" + " WHERE (1=1) ";
    		Map<String, Object> parametros = new HashMap<>(); 
    		
    		if ((idCapa != null) && !idCapa.equals("")) {
    			SQL = SQL + " AND (CAP.ID = :ID_CAPA OR CAT.ID_CAPA = :ID_CAPA)";
    			parametros.put(":ID_CAPA", idCapa);
    		}
    		
    		if ((idCategoria != null) && !idCategoria.equals("")) {
    			SQL = SQL + " AND CAT.ID = :ID_CATEGORIA ";
    			parametros.put(":ID_CATEGORIA", idCategoria);
    		}
    
    		if (!Utils.isNull.apply(fechaInicio) && !Utils.isNull.apply(fechaFin)) {
    			SQL = SQL + " :FILTER_RANGO_FECHAS ";
    			parametros.put(":FILTER_RANGO_FECHAS",
    					" AND VIG.D_FECHA_REPORTE BETWEEN TO_DATE('" + fechaInicio.format(formatter) + INIT_DAY
    							+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + fechaFin.format(formatter) + END_DAY
    							+ "', 'dd/MM/yyyy HH24:MI:SS') ");
    		}
    
    		if (estado != null) {
    			SQL = SQL + " AND VIG.S_ESTADO_REPORTE = ':ESTADO' ";
    			parametros.put(":ESTADO", estado);
    		}
    		
    		if (comenPen) {
    			SQL = SQL + " AND (SELECT COUNT (*) FROM VIGIAS.T247VIG_COMENTARIOS_REPORTE "
    					  + " WHERE S_ESTADO = 'PENDIENTE' AND ID_REPORTE = VIG.ID) > 0 ";
    		}
    
    		return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<Integer>) (rs, rowNum) -> {
    			return rs.getInt("CANTIDAD");
    		});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener el número de avistamientos {}{}--", e);
			return 0;
		}

	}	
	
	
	
	@Override
	public List<VigiaDto> getVigiaPaginatedPorParametros(Long idCapa, LocalDate desde, LocalDate hasta, 
			Long idCategoria, String whereClause, String orderClause, int pageStart, int pageSize, 
			String estadosList, boolean comenPen) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT * FROM (SELECT FILTERED_ORDERED_RESULTS.*, COUNT(1) OVER() total_records, ROWNUM AS RN " 
					+"FROM (SELECT BASEINFO.* FROM ("
					
					
					+ "SELECT VIG.ID AS ID_VIGIA, "
					+ "(CASE WHEN VIG.S_DESCRIPCION IS NULL THEN ' ' ELSE VIG.S_DESCRIPCION END ) AS DESCRIPCION_VIGIA, "
					+ "VIG.ID_USUARIO AS ID_USUARIO, "						
					+ "VIG.S_ESTADO_REPORTE AS ESTADO_REPORTE, "
					+ "VIG.D_FECHA_REPORTE AS FECHA_REPORTE, "
					+ "USU.USERNAME AS USERNAME_USUARIO, "
					
					+ "VIG.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					
					
					+ "MAR.ID_ICONO ID_ICONO, "
					+ "MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA, " 
					
					
					+ "(SELECT COR.X FROM CONTENEDOR.D247CON_MARCADOR M, "  
                    + "TABLE(SDO_UTIL.GETVERTICES(M.CLB_COORDENADA_PUNTO)) COR WHERE M.ID = VIG.ID_MARCADOR) AS LONGITUD ,"  
                    + "(SELECT COR.Y FROM CONTENEDOR.D247CON_MARCADOR M, "  
                    + "TABLE(SDO_UTIL.GETVERTICES(M.CLB_COORDENADA_PUNTO)) COR WHERE M.id = VIG.ID_MARCADOR) AS LATITUD "   
					
					
					+ "FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (VIG.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (VIG.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID)  "
					
					
					+ "WHERE 0=0 :FECHA :ID_CAPA :ID_CATEGORIA :ID_ESTADO :ID_ESTADO :COMENPEN "
					+ "ORDER BY ID_VIGIA DESC ";
			
			parametros.put(":FECHA", "");
			parametros.put(":ID_CAPA", "");
			parametros.put(":ID_CATEGORIA", "");
			parametros.put(":ID_ESTADO", "");
			parametros.put(":COMENPEN", "");
			
			if (!Utils.isNull.apply(desde)) {
				parametros.put(":FECHA",
						" AND VIG.D_FECHA_REPORTE BETWEEN TO_DATE('" + desde.format(formatter) + INIT_DAY
								+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + hasta.format(formatter) + END_DAY
								+ "', 'dd/MM/yyyy HH24:MI:SS') ");
			}			
			if (!Utils.isNull.apply(idCapa)) {
				parametros.put(":ID_CAPA", " AND (CAP.ID = " + idCapa + " OR CAT.ID_CAPA = " + idCapa + ")");
			}
			if (!Utils.isZero(idCategoria)) {
				parametros.put(":ID_CATEGORIA", " AND CAT.ID = " + idCategoria);
			}
			if ((estadosList!=null) && (!estadosList.equals(""))) {
				parametros.put(":ID_ESTADO", " AND VIG.S_ESTADO_REPORTE IN (" + estadosList + ")");
			}			
			if (comenPen) {
				parametros.put(":COMENPEN", 
			    " AND (SELECT COUNT (*) FROM VIGIAS.T247VIG_COMENTARIOS_REPORTE " +
			    " WHERE S_ESTADO = 'PENDIENTE' AND ID_REPORTE = VIG.ID) > 0 ");
			}

			SQL = SQL + " ) BASEINFO ) FILTERED_ORDERED_RESULTS :WHERE_CLAUSE :ORDER_CLASUE ) " 
					  + "WHERE RN > (:PAGE_START) AND RN <= (:PAGE_START +  :PAGE_SIZE)";
			
			parametros.put(":WHERE_CLAUSE", whereClause==null?"":whereClause);
			parametros.put(":ORDER_CLASUE", orderClause);
			parametros.put(":PAGE_START", pageStart);
			parametros.put(":PAGE_SIZE", pageSize);

			System.out.println(SQL(SQL, parametros));

			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros),
					(RowMapper<VigiaDto>) (rs, rowNum) -> {
						VigiaDto vigia = new VigiaDto();
						vigia.setId(rs.getLong("ID_VIGIA"));
						vigia.setDescripcion(rs.getString("DESCRIPCION_VIGIA"));
						vigia.setIdUsuario(rs.getLong("ID_USUARIO"));
						vigia.setEstado(rs.getString("ESTADO_REPORTE"));
						vigia.setFechaReporte(rs.getDate("FECHA_REPORTE"));
						vigia.setUsername(rs.getString("USERNAME_USUARIO"));
						vigia.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
						vigia.setRutaMultimedia(multimediaServerUrl + rs.getLong("ID_MULTIMEDIA"));
						vigia.setIdIcono(rs.getLong("ID_ICONO"));
						vigia.setRutaIcono(urlIconos + rs.getLong("ID_ICONO"));
						vigia.setIdMarcador(rs.getLong("ID_MARCADOR"));
						vigia.setLatitud(rs.getString("LATITUD"));
						vigia.setLongitud(rs.getString("LONGITUD"));
						return vigia;
					});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}
	
	
	
	@Override
	public int getCantidadVigiasPaginatedPorParametros(Long idCapa,Long idCategoria, String estadosList, 
			LocalDate fechaInicio, LocalDate fechaFin, boolean comenPen) throws SQLException {
		try {
    		SQL = "SELECT COUNT(*) AS CANTIDAD FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG "
    				+ " INNER JOIN CONTENEDOR.D247CON_MARCADOR MAR ON MAR.ID = VIG.ID_MARCADOR "
    				+ " LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON CAT.ID = MAR.ID_CATEGORIA " 
    		        + " LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON CAP.ID = MAR.ID_CAPA" + " WHERE (1=1) ";
    		
    		Map<String, Object> parametros = new HashMap<>(); 
    		
    		if (!Utils.isNull.apply(idCapa)) {
    			SQL = SQL + " AND (CAP.ID = :ID_CAPA OR CAT.ID_CAPA = :ID_CAPA)";
    			parametros.put(":ID_CAPA", idCapa);
    		}
    		
    		if (!Utils.isNull.apply(idCategoria)) {
    			SQL = SQL + " AND CAT.ID = :ID_CATEGORIA ";
    			parametros.put(":ID_CATEGORIA", idCategoria);
    		}
    
    		if (!Utils.isNull.apply(fechaInicio) && !Utils.isNull.apply(fechaFin)) {
    			SQL = SQL + " :FILTER_RANGO_FECHAS ";
    			parametros.put(":FILTER_RANGO_FECHAS",
    					" AND VIG.D_FECHA_REPORTE BETWEEN TO_DATE('" + fechaInicio.format(formatter) + INIT_DAY
    							+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + fechaFin.format(formatter) + END_DAY
    							+ "', 'dd/MM/yyyy HH24:MI:SS') ");
    		}
    
    		if (estadosList != null) {
    			SQL = SQL + " AND VIG.S_ESTADO_REPORTE IN (:ESTADOS_LIST) ";
    			parametros.put(":ESTADOS_LIST", estadosList);
    		}
    		
    		if (comenPen) {
    			SQL = SQL + " AND (SELECT COUNT (*) FROM VIGIAS.T247VIG_COMENTARIOS_REPORTE "
    					  + " WHERE S_ESTADO = 'PENDIENTE' AND ID_REPORTE = VIG.ID) > 0 ";
    		}
    
    		return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<Integer>) (rs, rowNum) -> {
    			return rs.getInt("CANTIDAD");
    		});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener el número de avistamientos {}{}--", e);
			return 0;
		}

	}	
	
	
	
	@Override 
	public int getCantidadVigiasPaginatedAndFilteredPorParametros(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria, String whereClause, String estadosList, 
			boolean comenPen) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT COUNT(1) TOTAL_RECORDS " 
					+"FROM (SELECT BASEINFO.* FROM ("
															
					+ "SELECT VIG.ID AS ID_VIGIA, "
					+ "(CASE WHEN VIG.S_DESCRIPCION IS NULL THEN ' ' ELSE VIG.S_DESCRIPCION END ) AS DESCRIPCION_VIGIA, "
					+ "VIG.ID_USUARIO AS ID_USUARIO, "						
					+ "VIG.S_ESTADO_REPORTE AS ESTADO_REPORTE, "
					+ "VIG.D_FECHA_REPORTE AS FECHA_REPORTE, "
					+ "USU.USERNAME AS USERNAME_USUARIO, "
					
					+ "VIG.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					
					
					+ "MAR.ID_ICONO ID_ICONO, "
					+ "MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA, " 
					
					
					+ "(SELECT COR.X FROM CONTENEDOR.D247CON_MARCADOR M, "  
                    + "TABLE(SDO_UTIL.GETVERTICES(M.CLB_COORDENADA_PUNTO)) COR WHERE M.ID = VIG.ID_MARCADOR) AS LONGITUD ,"  
                    + "(SELECT COR.Y FROM CONTENEDOR.D247CON_MARCADOR M, "  
                    + "TABLE(SDO_UTIL.GETVERTICES(M.CLB_COORDENADA_PUNTO)) COR WHERE M.id = VIG.ID_MARCADOR) AS LATITUD "   
					
					
					+ "FROM VIGIAS.T247VIG_REPORTE_VIGIA VIG "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (VIG.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (VIG.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID)  "
					
					
					+ "WHERE 0=0 :FECHA :ID_CAPA :ID_CATEGORIA :ID_ESTADO :ID_ESTADO :COMENPEN ";

			parametros.put(":FECHA", "");
			parametros.put(":ID_CAPA", "");
			parametros.put(":ID_CATEGORIA", "");
			parametros.put(":ID_ESTADO", "");
			parametros.put(":COMENPEN", "");
			
			if (!Utils.isNull.apply(desde)) {
				parametros.put(":FECHA",
						" AND VIG.D_FECHA_REPORTE BETWEEN TO_DATE('" + desde.format(formatter) + INIT_DAY
								+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + hasta.format(formatter) + END_DAY
								+ "', 'dd/MM/yyyy HH24:MI:SS') ");
			}			
			if (!Utils.isNull.apply(idCapa)) {
				parametros.put(":ID_CAPA", " AND (CAP.ID = " + idCapa + " OR CAT.ID_CAPA = " + idCapa + ")");
			}
			if (!Utils.isZero(idCategoria)) {
				parametros.put(":ID_CATEGORIA", " AND CAT.ID = " + idCategoria);
			}
			if ((estadosList!=null) && (!estadosList.equals(""))) {
				parametros.put(":ID_ESTADO", " AND VIG.S_ESTADO_REPORTE IN (" + estadosList + ")");
			}			
			if (comenPen) {
				parametros.put(":COMENPEN", 
			    " AND (SELECT COUNT (*) FROM VIGIAS.T247VIG_COMENTARIOS_REPORTE " +
			    " WHERE S_ESTADO = 'PENDIENTE' AND ID_REPORTE = VIG.ID) > 0 ");
			}

			SQL = SQL + " ) BASEINFO ) FILTERED_ORDERED_RESULTS :WHERE_CLAUSE  ";
			
			parametros.put(":WHERE_CLAUSE", whereClause==null?"":whereClause);
			
			System.out.println(SQL(SQL, parametros));
			
			return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<Integer>) (rs, rowNum) -> {
				return rs.getInt("TOTAL_RECORDS");
			});
		
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener el número de avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener el número de avistamientos {}{}--", e);
		}
	}
		

	public List<MarkerPoint> obtenerMarcadoresVigiaPorLatYLonYRadioYCapa(Double latitud, Double longitud, int radioAccion,
			Long idCapaCategoria, String nivelCapa) throws SQLException {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			SQL = " SELECT MAR.ID, t.X AS X, t.Y AS Y, MAR.ID_ICONO AS RUTA_LOGO FROM CONTENEDOR.D247CON_MARCADOR MAR, "
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t,"
					+ " VIGIAS.T247VIG_REPORTE_VIGIA VIG "
					+ " WHERE VIG.ID_MARCADOR = MAR.ID AND :ID_CAPA_CATEGORIA  AND  ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type"
					+ " (:LONGITUD,:LATITUD ,NULL),NULL,NULL), "
					+ " MAR.CLB_COORDENADA_PUNTO,0.0001,'unit=M'))) <= :RADIO"
					+ " AND ROWNUM <= 100 ORDER BY t.X,t.Y  ASC ";

			parametros.put(":ID_CAPA_CATEGORIA", TipoNivelCapa.CAPA.toString().equals(nivelCapa)
					? " MAR.ID_CAPA =" + idCapaCategoria + "" : " MAR.ID_CATEGORIA = " + idCapaCategoria + "");
			parametros.put(":LATITUD", latitud);
			parametros.put(":LONGITUD", longitud);
			parametros.put(":RADIO", radioAccion);
			
			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), (rs, rowNum) -> new MarkerPoint(rs.getLong(1),
				new Point(rs.getDouble(3), rs.getDouble(2)), urlIconos+rs.getString(4)));
			
		} catch (Exception e) {
			LOGGER.error("Error al obtener los reportes de vigía por capa y radio accion {}{}--",
					(" id capa :" + idCapaCategoria + " radio accion :" + radioAccion), e);
			throw new SQLException("Error al obtener los reportes de vigía por capa y radio accion {}{}--", e);
		}
	}	
	
	
	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vigia> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vigia buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
