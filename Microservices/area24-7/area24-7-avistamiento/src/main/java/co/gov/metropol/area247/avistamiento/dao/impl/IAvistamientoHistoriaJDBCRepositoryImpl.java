package co.gov.metropol.area247.avistamiento.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoHistoriaJDBCRepository;
import co.gov.metropol.area247.avistamiento.model.Historia;
import co.gov.metropol.area247.avistamiento.model.dto.HistoriaDto;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Transactional
@Repository
public class IAvistamientoHistoriaJDBCRepositoryImpl extends Dao implements IAvistamientoHistoriaJDBCRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(IAvistamientoHistoriaJDBCRepositoryImpl.class);

	private static String SQL = "";

	@Override
	public HistoriaDto obtenerHistoriaPorId(Long idHistoria) {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT HIST.ID AS ID_HISTORIA, HIST.S_TITULO AS TITULO, HIST.S_TEXTO AS TEXTO, "
					+ "HIST.N_ESTADO ESTADO_PUBLICACION, USU.ID AS ID_USUARIO, "
					+ "CASE WHEN USU.USERNAME IS NOT NULL THEN USU.USERNAME ELSE ' ' END AS USERNAME, "									
					+ "HIST.D_PUBLICACION AS FECHA_CREACION "
					+ "FROM AVISTAM.T247AVI_HISTORIA HIST "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (HIST.ID_USUARIO = USU.ID) "
					+ "WHERE HIST.ID = :ID_HISTORIA";

			parametros.put("ID_HISTORIA", idHistoria);

			return getNamedParameterJdbcTemplate().queryForObject(SQL, parametros,
					(RowMapper<HistoriaDto>) (rs, rowNum) -> {
						HistoriaDto historiaDto = new HistoriaDto();
						historiaDto.setId(rs.getLong("ID_HISTORIA"));
						historiaDto.setTitulo(rs.getString("TITULO"));
						historiaDto.setTexto(rs.getString("TEXTO"));
						historiaDto.setEstadoPublicacion(rs.getInt("ESTADO_PUBLICACION"));
						historiaDto.setIdUsuario(rs.getLong("ID_USUARIO"));
						historiaDto.setUsername(rs.getString("USERNAME"));
						historiaDto.setFechaCreacion(rs.getDate("FECHA_CREACION"));
						return historiaDto;
					});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public List<HistoriaDto> obtenerHistoriaPorParametros(Long idAvistamiento, Long idUsuario, Integer estado) {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT HIST.ID AS ID_HISTORIA, HIST.S_TITULO AS TITULO, HIST.S_TEXTO AS TEXTO, "
					+ "HIST.N_ESTADO ESTADO_PUBLICACION, "
					+ "CASE WHEN USU.USERNAME IS NOT NULL THEN USU.USERNAME ELSE ' ' END AS USERNAME, "	
					+ "USU.USERNAME AS USERNAME, HIST.D_PUBLICACION AS FECHA_CREACION "
					+ "FROM AVISTAM.T247AVI_HISTORIA HIST "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (HIST.ID_USUARIO = USU.ID) WHERE (1=1) ";

			if (idAvistamiento != null) {
				SQL = SQL + " AND HIST.ID_AVISTAMIENTO = :ID_AVISTAMIENTO ";
				parametros.put("ID_AVISTAMIENTO", idAvistamiento);
			}
			if (idUsuario != null) {
				SQL = SQL + " AND HIST.ID_USUARIO = :ID_USUARIO ";
				parametros.put("ID_USUARIO", idUsuario);
			}
			if (estado != null) {
				SQL = SQL + " AND HIST.N_ESTADO = :ESTADO ";
				parametros.put("ESTADO", estado);
			}
			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<HistoriaDto>) (rs, rowNum) -> {
				HistoriaDto historiaDto = new HistoriaDto();
				historiaDto.setId(rs.getLong("ID_HISTORIA"));
				historiaDto.setTitulo(rs.getString("TITULO"));
				historiaDto.setTexto(rs.getString("TEXTO"));
				historiaDto.setEstadoPublicacion(rs.getInt("ESTADO_PUBLICACION"));
				historiaDto.setIdUsuario(rs.getLong("ID_USUARIO"));
				historiaDto.setUsername(rs.getString("USERNAME"));
				historiaDto.setFechaCreacion(rs.getDate("FECHA_CREACION"));
				return historiaDto;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public List<HistoriaDto> historiaObtenerPorIdAvistamiento(Long idAvistamiento, String username) {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT HIST.ID AS ID_HISTORIA, HIST.S_TITULO AS TITULO, HIST.S_TEXTO AS TEXTO, "
					+ "HIST.N_ESTADO ESTADO_PUBLICACION, USU.ID AS ID_USUARIO, "
					+ "USU.USERNAME AS USERNAME, HIST.D_PUBLICACION AS FECHA_CREACION "
					+ "FROM AVISTAM.T247AVI_HISTORIA HIST "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (USU.ID = HIST.ID_USUARIO) ";

			if (idAvistamiento != null && username != "" && username != null) {
				SQL += " WHERE HIST.ID_AVISTAMIENTO = :ID_AVISTAMIENTO AND USU.USERNAME=:USERNAME AND HIST.N_ESTADO <> 0" + " UNION "
						+ "SELECT HIST.ID AS ID_HISTORIA, HIST.S_TITULO AS TITULO, HIST.S_TEXTO AS TEXTO, "
						+ "HIST.N_ESTADO ESTADO_PUBLICACION, USU.ID AS ID_USUARIO, "
						+ "CASE WHEN USU.USERNAME IS NOT NULL THEN USU.USERNAME ELSE ' ' END AS USERNAME, "	
						+ "HIST.D_PUBLICACION AS FECHA_CREACION "
						+ "FROM AVISTAM.T247AVI_HISTORIA HIST "
						+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (USU.ID = HIST.ID_USUARIO) "
						+ " WHERE HIST.ID_AVISTAMIENTO =:ID_AVISTAMIENTO AND HIST.N_ESTADO = 1 AND HIST.N_ESTADO <> 0";

				parametros.put("ID_AVISTAMIENTO", idAvistamiento);
				parametros.put("USERNAME", username);
			} else {
				SQL += " WHERE HIST.ID_AVISTAMIENTO = :ID_AVISTAMIENTO AND N_ESTADO = 1";
				parametros.put("ID_AVISTAMIENTO", idAvistamiento);
			}

			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<HistoriaDto>) (rs, rowNum) -> {
				HistoriaDto historiaDto = new HistoriaDto();
				historiaDto.setId(rs.getLong("ID_HISTORIA"));
				historiaDto.setTitulo(rs.getString("TITULO"));
				historiaDto.setTexto(rs.getString("TEXTO"));
				historiaDto.setEstadoPublicacion(rs.getInt("ESTADO_PUBLICACION"));
				historiaDto.setIdUsuario(rs.getLong("ID_USUARIO"));
				historiaDto.setUsername(rs.getString("USERNAME"));
				
				historiaDto.setFechaCreacion(rs.getDate("FECHA_CREACION"));
				return historiaDto;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	public List<HistoriaDto> getHistoriasPorIdAvistamiento(Long idAvistamiento) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<>();

			SQL = "SELECT HIST.ID AS ID_HISTORIA, HIST.S_TITULO AS TITULO, HIST.S_TEXTO AS TEXTO, "
					+ "HIST.N_ESTADO ESTADO_PUBLICACION, USU.ID AS ID_USUARIO, "
					+ "CASE WHEN USU.USERNAME IS NOT NULL THEN USU.USERNAME ELSE ' ' END AS USERNAME, "	
					+ "HIST.D_PUBLICACION AS FECHA_CREACION, "
					+ "(SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA "
					+ " WHERE N_ESTADO = 2 AND ID_HISTORIA = HIST.ID) AS COMENTARIOS_PENDIENTES, "
					+ "(SELECT COUNT (*) FROM AVISTAM.T247AVI_COMENTARIO_HISTORIA "
					+ " WHERE ID_HISTORIA = HIST.ID) AS TOTAL_COMENTARIOS "
					+ "FROM AVISTAM.T247AVI_HISTORIA HIST "
					+ "LEFT JOIN CONTENEDOR.T247SEG_USUARIO USU ON (USU.ID = HIST.ID_USUARIO) "
					+ " WHERE HIST.ID_AVISTAMIENTO = :ID_AVISTAMIENTO";

			parametros.put("ID_AVISTAMIENTO", idAvistamiento);

			return getNamedParameterJdbcTemplate().query(SQL, parametros, (RowMapper<HistoriaDto>) (rs, rowNum) -> {
				HistoriaDto historiaDto = new HistoriaDto();
				historiaDto.setId(rs.getLong("ID_HISTORIA"));
				historiaDto.setTitulo(rs.getString("TITULO"));
				historiaDto.setTexto(rs.getString("TEXTO"));
				historiaDto.setEstadoPublicacion(rs.getInt("ESTADO_PUBLICACION"));
				historiaDto.setIdUsuario(rs.getLong("ID_USUARIO"));
				historiaDto.setUsername(rs.getString("USERNAME"));
				historiaDto.setFechaCreacion(rs.getDate("FECHA_CREACION"));
				historiaDto.setCantidadComenPen(rs.getInt("COMENTARIOS_PENDIENTES"));
				historiaDto.setCantidadComenTotal(rs.getInt("TOTAL_COMENTARIOS"));
				return historiaDto;
			});
		} catch (Exception e) {
			LOGGER.error("Error en el repositorio al obtener los avistamientos {}{}--", e);
			throw new SQLException("Error en el repositorio al obtener los avistamientos {}{}--", e);
		}
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Historia> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Historia buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
