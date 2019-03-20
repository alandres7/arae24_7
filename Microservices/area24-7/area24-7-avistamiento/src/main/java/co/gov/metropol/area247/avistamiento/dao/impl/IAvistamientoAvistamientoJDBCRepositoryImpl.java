package co.gov.metropol.area247.avistamiento.dao.impl;

import java.sql.ResultSet;
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

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoAvistamientoJDBCRepository;
import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.dto.AvistamientoDto;
import co.gov.metropol.area247.avistamiento.model.dto.FloraDto;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import co.gov.metropol.area247.jdbc.util.Utils;
import io.reactivex.Observable;

@Transactional
@Repository
public class IAvistamientoAvistamientoJDBCRepositoryImpl extends Dao
		implements IAvistamientoAvistamientoJDBCRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(IAvistamientoAvistamientoJDBCRepositoryImpl.class);
	
	@Value("${media.server.url}")
	private String multimediaServerUrl;

	private static String SQL = "";

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	private static final String INIT_DAY = " 00:00:00";

	private static final String END_DAY = " 23:59:59";

	private static final Long CAPA_INVENTARIO_FLORA = 211L;

	@Override
	public List<AvistamientoDto> buscarTodosAvistamientos() throws SQLException {
		try {
			SQL = "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP WHERE CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT WHERE CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA," + " MUL.S_RUTA AS RUTA_MULTIMEDIA, VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) " + "ORDER BY AVI.ID DESC";
			return getNamedParameterJdbcTemplate().query(SQL, (RowMapper<AvistamientoDto>) (rs, rowNum) -> {
				AvistamientoDto avistamiento = new AvistamientoDto();
				avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
				avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
				avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
				avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
				avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
				avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
				avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
				avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
				avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
				avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
				avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
				avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
				avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
				avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
				return avistamiento;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public AvistamientoDto buscarAvistamientoPorIdAvistamientoOIdMarcador(Long idAvistamiento, Long idMarcador)
			throws SQLException {
		try {
			SQL = "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP WHERE CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT WHERE CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA," + " MUL.S_RUTA AS RUTA_MULTIMEDIA, VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, AVI.ID_ESPECIE AS ID_ESPECIE, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.T247CON_MULTIMEDIA MUL ON (MUL.ID = VEN.ID_MULTIMEDIA) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) "
					+ "WHERE :FILTER_AVISTAMIENTO :FILTER_MARCADOR";

			Map<String, Object> parametros = new HashMap<>();

			parametros.put(":FILTER_AVISTAMIENTO", "");
			parametros.put(":FILTER_MARCADOR", "");

			if (!Utils.isZero(idAvistamiento)) {
				parametros.put(":FILTER_AVISTAMIENTO", " AVI.ID = " + idAvistamiento);
			}

			if (!Utils.isZero(idMarcador)) {
				parametros.put(":FILTER_MARCADOR", " MAR.ID = " + idMarcador);
			}

			return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<AvistamientoDto>) (rs, rowNum) -> {
				AvistamientoDto avistamiento = new AvistamientoDto();
				avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
				avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
				avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
				avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
				avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
				avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
				avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
				avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
				avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
				avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
				avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				avistamiento.setRutaMultimedia(rs.getString("RUTA_MULTIMEDIA"));
				avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
				avistamiento.setIdEspecie(hasColumnResultSet(rs,"ID_ESPECIE") ? rs.getLong("ID_ESPECIE") : null);
				avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
				avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
				avistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
			
				return avistamiento;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public List<AvistamientoDto> obtenerAvistamientoPorEstado(int estado) throws SQLException {
		try {
			SQL = "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP WHERE CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT WHERE CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA ," + "	VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) "
					+ "WHERE AVI.N_ESTADO = :ESTADO";

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ESTADO", estado);
			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<AvistamientoDto>) (rs, rowNum) -> {
				AvistamientoDto avistamiento = new AvistamientoDto();
				avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
				avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
				avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
				avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
				avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
				avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
				avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
				avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
				avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
				avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
				avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
				avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
				avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
				avistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
				return avistamiento;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public List<AvistamientoDto> obtenerAvistamientoPorIdUsuario(Long idUsuario) throws SQLException {
		try {
			SQL = "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP WHERE CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT WHERE CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA ," + "	VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) " + "WHERE USU.ID = :ID_USUARIO";

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ID_USUARIO", idUsuario);
			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<AvistamientoDto>) (rs, rowNum) -> {
				AvistamientoDto avistamiento = new AvistamientoDto();
				avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
				avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
				avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
				avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
				avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
				avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
				avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
				avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
				avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
				avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
				avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
				avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
				avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
				avistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
				return avistamiento;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public Integer cantidadAvistamientoPorEstado(int estado) throws SQLException {
		try {
			SQL = "SELECT COUNT(*) AS CANTIDAD FROM AVISTAM.T247AVI_AVISTAMIENTO AVI WHERE AVI.N_ESTADO = :ESTADO";
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ESTADO", estado);
			return getNamedParameterJdbcTemplate().queryForObject(SQL, parametros,
					(RowMapper<Integer>) (rs, rowNum) -> {
						return rs.getInt("CANTIDAD");
					});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public List<AvistamientoDto> obtenerAvistamientoPorParametros(String nombreComun, String nombreCientifico,
			String municipio, String tipoAvistamiento) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP WHERE CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT WHERE CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA ," + "	VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID) WHERE (1=1) ";

			if ((nombreComun != null) && !nombreComun.equals("")) {
				SQL = SQL + " AND AVI.S_NOMBRE_COMUN = :NOMBRE_COMUN ";
				parametros.put("NOMBRE_COMUN", nombreComun);
			}
			if ((nombreCientifico != null) && !nombreCientifico.equals("")) {
				SQL = SQL + " AND AVI.S_NOMBRE_CIENTIFICO = :NOMBRE_CIENTIFICO ";
				parametros.put("NOMBRE_CIENTIFICO", nombreCientifico);
			}
			if ((tipoAvistamiento != null) && !tipoAvistamiento.equals("")) {
				SQL = SQL + " AND AVI.S_TIPO_AVISTAMIENTO = :TIPO_AVISTAMIENTO ";
				parametros.put("TIPO_AVISTAMIENTO", tipoAvistamiento);
			}
			if ((municipio != null) && !municipio.equals("")) {
				SQL = SQL + " AND MAR.S_MUNICIPIO = :MUNICIPIO ";
				parametros.put("MUNICIPIO", municipio);
			}

			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<AvistamientoDto>) (rs, rowNum) -> {
				AvistamientoDto avistamiento = new AvistamientoDto();
				avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
				avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
				avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
				avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
				avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
				avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
				avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
				avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
				avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
				avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
				avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
				avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
				avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
				avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
				avistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
				return avistamiento;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public Observable<Integer> obtenerNumeroAvistamientoPorTipoAvistamientoOrEstadoOrFecha(String idCapa,
			String idCategoria, Integer estado, LocalDate fechaInicio, LocalDate fechaFin, 
			boolean soloComHis, boolean conComenDeHis) throws SQLException {

		SQL = "SELECT COUNT(*) AS CANTIDAD FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
				+ " INNER JOIN CONTENEDOR.D247CON_MARCADOR MAR ON MAR.ID = AVI.ID_MARCADOR "
				+ " LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON CAT.ID = MAR.ID_CATEGORIA" 
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
					" AND AVI.D_PUBLICACION BETWEEN TO_DATE('" + fechaInicio.format(formatter) + INIT_DAY
							+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + fechaFin.format(formatter) + END_DAY
							+ "', 'dd/MM/yyyy HH24:MI:SS') ");
		}

		if (estado != null) {
			SQL = SQL + " AND AVI.N_ESTADO = :ESTADO ";
			parametros.put(":ESTADO", estado);
		}
		
		if (soloComHis) {
			SQL = SQL + " AND ((SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0 " +
			            " OR   (SELECT COUNT (*) FROM AVISTAM.T247AVI_HISTORIA   WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0) ";
		}
		
		if (conComenDeHis) {
			SQL = SQL + " AND (SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA   WHERE N_ESTADO = 2 AND ID_HISTORIA " + 
					    "      IN (SELECT ID FROM AVISTAM.T247AVI_HISTORIA WHERE ID_AVISTAMIENTO = AVI.ID)) > 0 ";
		}

		return Observable.create(emitter -> {
			try {
				emitter.onNext(
						getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<Integer>) (rs, rowNum) -> {
							return rs.getInt("CANTIDAD");
						}));
			} catch (Exception e) {
				emitter.onError(new SQLException(
						"Error al consultar la cantidad de avistamientos por tipo avistamiento o tipo especie o fechas --{}{}"
								+ ("tipo avistamiento: " + idCapa + "estado: " + estado),
						e));
			}
		});

	}

	@Override
	public List<AvistamientoDto> obtenerAvistamientoPorCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP where CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT where CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA, VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID)  WHERE :FECHA :ID_CAPA :ID_CATEGORIA";

			parametros.put(":ID_CAPA", "");
			parametros.put(":FECHA", "");
			parametros.put(":ID_CATEGORIA", "");

			if (!Utils.isNull.apply(desde)) {
				parametros.put(":FECHA",
						" AVI.D_PUBLICACION BETWEEN TO_DATE('" + desde.format(formatter) + INIT_DAY
								+ "','dd/MM/YYYY HH24:MI:SS') AND TO_DATE('" + hasta.format(formatter) + END_DAY
								+ "', 'dd/MM/yyyy HH24:MI:SS') ");

				if (!Utils.isNull.apply(idCapa)) {
					parametros.put(":ID_CAPA", " AND (CAP.ID = " + idCapa + " OR CAT.ID_CAPA = " + idCapa + ")");
				}
				if (!Utils.isZero(idCategoria)) {
					parametros.put(":ID_CATEGORIA", " AND CAT.ID = " + idCategoria);
				}

			} else if (!Utils.isNull.apply(idCapa)) {
				parametros.put(":ID_CAPA", " (CAP.ID = " + idCapa + " OR CAT.ID_CAPA = " + idCapa + ")");
				if (!Utils.isZero(idCategoria)) {
					parametros.put(":ID_CATEGORIA", " AND CAT.ID = " + idCategoria);
				}
			} else if (!Utils.isZero(idCategoria)) {
				parametros.put(":ID_CATEGORIA", " CAT.ID = " + idCategoria);
			} else {
				SQL = SQL.replace("WHERE", " ");
			}

			SQL = SQL + " ORDER BY AVI.ID DESC";

			System.out.println(SQL(SQL, parametros));

			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros),
					(RowMapper<AvistamientoDto>) (rs, rowNum) -> {
						AvistamientoDto avistamiento = new AvistamientoDto();
						avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
						avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
						avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
						avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
						avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
						avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
						avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
						avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
						avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
						avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
						avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
						avistamiento.setRutaMultimedia(multimediaServerUrl + rs.getLong("ID_MULTIMEDIA"));
						avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
						avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
						avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
						avistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
						avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
						return avistamiento;
					});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}
	
	@Override
	public List<AvistamientoDto> getAvistamientoPaginatedPorCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria, String whereClause, String orderClause, int pageStart, int pageSize, String estadosList,
			boolean soloComHis, boolean conComenDeHis) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT * FROM (SELECT FILTERED_ORDERED_RESULTS.*, COUNT(1) OVER() total_records, ROWNUM AS RN " 
					+"FROM (SELECT BASEINFO.* FROM ("
					+ "SELECT AVI.ID AS ID_AVISTAMIENTO, "
					+ "(CASE WHEN AVI.S_NOMBRE_COMUN IS NULL THEN ' ' ELSE AVI.S_NOMBRE_COMUN END ) AS NOMBRE_COMUN, "
					+ "(CASE WHEN AVI.S_NOMBRE_CIENTIFICO IS NULL THEN ' ' ELSE AVI.S_NOMBRE_CIENTIFICO END) AS NOMBRE_CIENTIFICO, "
					+ "(CASE WHEN AVI.S_DESCRIPCION IS NULL THEN ' ' ELSE AVI.S_DESCRIPCION END ) AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, "					
					+ "(CASE WHEN CAT.S_NOMBRE IS NULL THEN CAP.S_NOMBRE ELSE CAT.S_NOMBRE END) AS TIPO_AVISTAMIENTO, "
					+ "(CASE WHEN ESP.S_NOMBRE IS NULL THEN 'Sin especie' ELSE ESP.S_NOMBRE END) AS TIPO_ESPECIE, "					
					+ "AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL AND MAR.ID_CATEGORIA IS NOT NULL THEN"
					+ " (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT where CAT.ID = MAR.ID_CATEGORIA)"
					+ " ELSE (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP where CAP.ID = MAR.ID_CAPA) END)"
					+ " AS TIENE_HISTORIA, VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " 					
					+ "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN AVISTAM.T247AVI_ESPECIE ESP ON (AVI.ID_ESPECIE = ESP.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID)  "
					+ "WHERE 0=0 :FECHA :ID_CAPA :ID_CATEGORIA :ID_ESTADO :ID_ESTADO :SOLOCOMHIS :CONCOMENDEHIS "
					+ "ORDER BY AVI.ID DESC ";
			
			parametros.put(":FECHA", "");
			parametros.put(":ID_CAPA", "");
			parametros.put(":ID_CATEGORIA", "");
			parametros.put(":ID_ESTADO", "");
			parametros.put(":SOLOCOMHIS", "");
			parametros.put(":CONCOMENDEHIS", "");
			
			if (!Utils.isNull.apply(desde)) {
				parametros.put(":FECHA",
						" AND AVI.D_PUBLICACION BETWEEN TO_DATE('" + desde.format(formatter) + INIT_DAY
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
				parametros.put(":ID_ESTADO", " AND AVI.N_ESTADO IN (" + estadosList + ")");
			}			
			if (soloComHis) {
				parametros.put(":SOLOCOMHIS", 
			    " AND ((SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0 " +
				"   OR (SELECT COUNT (*) FROM AVISTAM.T247AVI_HISTORIA   WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0) ");
			}
			if (conComenDeHis) {
				parametros.put(":CONCOMENDEHIS", 
			    " AND (SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA   WHERE N_ESTADO = 2 AND ID_HISTORIA " +
				"      IN (SELECT ID FROM AVISTAM.T247AVI_HISTORIA WHERE ID_AVISTAMIENTO = AVI.ID)) > 0 ");
			}			

			SQL = SQL + " ) BASEINFO ) FILTERED_ORDERED_RESULTS :WHERE_CLAUSE :ORDER_CLASUE ) " 
					  + "WHERE RN > (:PAGE_START) AND RN <= (:PAGE_START +  :PAGE_SIZE)";
			
			parametros.put(":WHERE_CLAUSE", whereClause==null?"":whereClause);
			parametros.put(":ORDER_CLASUE", orderClause);
			parametros.put(":PAGE_START", pageStart);
			parametros.put(":PAGE_SIZE", pageSize);

			System.out.println(SQL(SQL, parametros));

			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros),
					(RowMapper<AvistamientoDto>) (rs, rowNum) -> {
						AvistamientoDto avistamiento = new AvistamientoDto();
						avistamiento.setId(rs.getLong("ID_AVISTAMIENTO"));
						avistamiento.setNombreComun(rs.getString("NOMBRE_COMUN"));
						avistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO"));
						avistamiento.setDescripcion(rs.getString("DESCRIPCION_AVISTAMIENTO"));
						avistamiento.setIdUsuario(rs.getLong("ID_USUARIO"));
						avistamiento.setTipoAvistamiento(rs.getString("TIPO_AVISTAMIENTO"));
						avistamiento.setTipoEspecie(rs.getString("TIPO_ESPECIE"));
						avistamiento.setEstadoPublicacion(rs.getInt("ESTADO_AVISTAMIENTO"));
						avistamiento.setFechaCreacion(rs.getDate("FECHA_REGISTRO"));
						avistamiento.setUsername(rs.getString("USERNAME_USUARIO"));
						avistamiento.setIdMultimedia(rs.getLong("ID_MULTIMEDIA"));
						avistamiento.setRutaMultimedia(multimediaServerUrl + rs.getLong("ID_MULTIMEDIA"));
						avistamiento.setIdIcono(rs.getLong("ID_ICONO"));
						avistamiento.setIdMarcador(rs.getLong("ID_MARCADOR"));
						avistamiento.setIdCapa(rs.getLong("ID_CAPA"));
						avistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
						avistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA") == 1 ? Boolean.TRUE : Boolean.FALSE);
						return avistamiento;
					});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}
	
	@Override
	public int getNumFilteredAvistamXCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria, String whereClause, String estadosList, 
			boolean soloComHis, boolean conComenDeHis) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT COUNT(1) TOTAL_RECORDS " 
					+"FROM (SELECT BASEINFO.* FROM ("
					+ "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP where CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT where CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA, VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID)  "
					+ "WHERE 0=0 :FECHA :ID_CAPA :ID_CATEGORIA :ID_ESTADO :SOLOCOMHIS :CONCOMENDEHIS ";

			parametros.put(":FECHA", "");
			parametros.put(":ID_CAPA", "");			
			parametros.put(":ID_CATEGORIA", "");
			parametros.put(":ID_ESTADO", "");
            parametros.put(":SOLOCOMHIS", "");
            parametros.put(":CONCOMENDEHIS", "");
			
			if (!Utils.isNull.apply(desde)) {
				parametros.put(":FECHA",
						" AND AVI.D_PUBLICACION BETWEEN TO_DATE('" + desde.format(formatter) + INIT_DAY
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
				parametros.put(":ID_ESTADO", " AND AVI.N_ESTADO IN (" + estadosList + ")");
			}			
			if (soloComHis) {
				parametros.put(":SOLOCOMHIS", 
			    " AND ((SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0 " +
				"   OR (SELECT COUNT (*) FROM AVISTAM.T247AVI_HISTORIA   WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0) ");
			}
			if (conComenDeHis) {
				parametros.put(":CONCOMENDEHIS", 
			    " AND (SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA   WHERE N_ESTADO = 2 AND ID_HISTORIA " +
				"      IN (SELECT ID FROM AVISTAM.T247AVI_HISTORIA WHERE ID_AVISTAMIENTO = AVI.ID)) > 0 ");
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
	
	@Override
	public int getNumAvistamXCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria, String estadosList, boolean soloComHis, boolean conComenDeHis) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT COUNT(ID_AVISTAMIENTO) CANTIDAD FROM ("
					+ "SELECT AVI.ID AS ID_AVISTAMIENTO, AVI.S_NOMBRE_COMUN AS NOMBRE_COMUN, "
					+ "AVI.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ "AVI.ID_USUARIO AS ID_USUARIO, AVI.S_TIPO_AVISTAMIENTO AS TIPO_AVISTAMIENTO, "
					+ "AVI.S_TIPO_ESPECIE AS TIPO_ESPECIE, AVI.N_ESTADO AS ESTADO_AVISTAMIENTO, "
					+ "AVI.D_PUBLICACION AS FECHA_REGISTRO, USU.USERNAME AS USERNAME_USUARIO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP where CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT where CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA, VEN.ID_MULTIMEDIA AS ID_MULTIMEDIA, "
					+ "MAR.ID_ICONO ID_ICONO, MAR.ID ID_MARCADOR, "
					+ "CASE WHEN MAR.ID_CAPA IS NOT NULL THEN MAR.ID_CAPA ELSE  CAT.ID_CAPA END AS ID_CAPA, "
					+ "MAR.ID_CATEGORIA AS ID_CATEGORIA " + "FROM AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "LEFT JOIN CONTENEDOR.D247CON_MARCADOR MAR ON (AVI.ID_MARCADOR = MAR.ID) "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (AVI.ID_USUARIO = USU.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_VENTANA_INFORMACION VEN ON (MAR.ID_VENTANA_INFO = VEN.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON (MAR.ID_CATEGORIA = CAT.ID) "
					+ "LEFT JOIN CONTENEDOR.D247CON_CAPA CAP ON (MAR.ID_CAPA = CAP.ID)  "
					+ "WHERE 0=0 :FECHA :ID_CAPA :ID_CATEGORIA :ID_ESTADO :SOLOCOMHIS :CONCOMENDEHIS )";

			parametros.put(":FECHA", "");
			parametros.put(":ID_CAPA", "");			
			parametros.put(":ID_CATEGORIA", "");
			parametros.put(":ID_ESTADO", "");
            parametros.put(":SOLOCOMHIS", "");
            parametros.put(":CONCOMENDEHIS", "");
			
			if (!Utils.isNull.apply(desde)) {
				parametros.put(":FECHA",
						" AND AVI.D_PUBLICACION BETWEEN TO_DATE('" + desde.format(formatter) + INIT_DAY
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
				parametros.put(":ID_ESTADO", " AND AVI.N_ESTADO IN (" + estadosList + ")");
			}			
			if (soloComHis) {
				parametros.put(":SOLOCOMHIS", 
			    " AND ((SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0 " +
				"   OR (SELECT COUNT (*) FROM AVISTAM.T247AVI_HISTORIA   WHERE N_ESTADO = 2 AND ID_AVISTAMIENTO = AVI.ID) > 0) ");
			}
			if (conComenDeHis) {
				parametros.put(":CONCOMENDEHIS", 
			    " AND (SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA   WHERE N_ESTADO = 2 AND ID_HISTORIA " +
				"      IN (SELECT ID FROM AVISTAM.T247AVI_HISTORIA WHERE ID_AVISTAMIENTO = AVI.ID)) > 0 ");
			}

			return getJdbcTemplate().queryForObject(SQL(SQL, parametros), (RowMapper<Integer>) (rs, rowNum) -> {
				return rs.getInt("CANTIDAD");
			});
		
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener el número de avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener el número de avistamientos {}{}--", e);
		}
	}

	@Override
	public List<FloraDto> getFloraInventory(int limInf, int limSup) throws SQLException {
		try {
			Map<String, Object> param = new HashMap<>();
			SQL = "SELECT S_NOMBRE_CIENTIFICO, S_NOMBRE_COMUN,"
					+ "					S_TIPO_ARBOL, S_FOTO, CORX, CORY FROM ("
					+ "SELECT RAW_FLORA_.*, ROWNUM ID_RAW_ FROM "
					+ "(SELECT DISTINCT S_NOMBRE_CIENTIFICO, S_NOMBRE_COMUN,"
					+ "					S_TIPO_ARBOL, S_FOTO, CORX, CORY " + "					FROM VWS_FLORA "
					+ "                    ORDER BY CORX DESC) RAW_FLORA_ ) "
					+ "                    WHERE ID_RAW_ BETWEEN :LIM_INF AND :LIM_SUP";
			param.put("LIM_INF", limInf);
			param.put("LIM_SUP", limSup);
			
			//Observable<List<FloraDto>> observableListFlorDto = Observable.fromArray(getNamedParameterJdbcTemplate().query(SQL, param,
			//		(RowMapper<FloraDto>) (rs, rowNum) -> cargarFloraDto(rs) ));
			
			return getNamedParameterJdbcTemplate().query(SQL, param,
					(RowMapper<FloraDto>) (rs, rowNum) -> cargarFloraDto(rs) );
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener el inventario de flora {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener el inventario de flora {}{}--", e);
		}
	}

	@Override
	public int countFloraInventory() throws SQLException {
		Map<String, Object> param = new HashMap<>();
		SQL = "SELECT COUNT(*) TAM FROM (SELECT DISTINCT S_NOMBRE_CIENTIFICO, S_NOMBRE_COMUN,"
				+ "S_TIPO_ARBOL, S_FOTO, CORX, CORY " + "FROM VWS_FLORA)";

		return getNamedParameterJdbcTemplate()
				.query(SQL, param, (RowMapper<Integer>) (rs, rowNum) -> new Integer(rs.getInt("TAM"))).get(0);

	}
	
	@Override
	public int countFloraInventoryRegistered() throws SQLException {
		Map<String, Object> param = new HashMap<>();
		SQL = "select count(id) tam " + 
				"  from avistam.t247avi_avistamiento " + 
				"where id_marcador in(select id from contenedor.d247con_marcador where id_capa = "+CAPA_INVENTARIO_FLORA+")";

		return getNamedParameterJdbcTemplate()
				.query(SQL, param, (RowMapper<Integer>) (rs, rowNum) -> new Integer(rs.getInt("tam"))).get(0);

	}

	public FloraDto cargarFloraDto(ResultSet rs) {
		try {
			FloraDto floraDto = new FloraDto(rs.getString("S_NOMBRE_CIENTIFICO"), rs.getString("S_NOMBRE_COMUN"),
					rs.getString("S_TIPO_ARBOL"), rs.getString("S_FOTO"), rs.getDouble("CORX"), rs.getDouble("CORY"));
			return floraDto;
		} catch (Exception e) {
			LOGGER.error("Error al cargar objeto floraDto {}{}--", e);
			throw new SQLException("Error al cargar objeto floraDto {}{}--", e);
		}
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Avistamiento> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Avistamiento buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
