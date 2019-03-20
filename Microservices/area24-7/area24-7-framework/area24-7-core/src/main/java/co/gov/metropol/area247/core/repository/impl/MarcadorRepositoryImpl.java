package co.gov.metropol.area247.core.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.gov.metropol.area247.core.domain.marker.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.metropol.area247.core.domain.en.TipoNivelCapa;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.ordenamiento.dto.CategoryRelationship;
import co.gov.metropol.area247.core.repository.MarcadorRepository;
import co.gov.metropol.area247.core.repository.rowmapper.MarcadorRowMapper;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
@Scope("prototype")
@Transactional(readOnly = true)
public class MarcadorRepositoryImpl extends Dao implements MarcadorRepository {

	@Value("${iconos.server.url}")
	String urlIconos;

	private static Logger LOGGER = LoggerFactory.getLogger(MarcadorRepositoryImpl.class);

	private static String SQL = "";

	public List<MarkerPoint> obtenerMarcadoresPorLatYLonYRadioYCapa(Double latitud, Double longitud, int radioAccion,
			Long idCapaCategoria, String nivelCapa) throws SQLException {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			SQL = " SELECT MAR.ID, t.X AS X, t.Y AS Y, MAR.ID_ICONO AS RUTA_LOGO FROM CONTENEDOR.D247CON_MARCADOR MAR, "
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t,"
					+ "  AVISTAM.T247AVI_AVISTAMIENTO AVI"
					+ " WHERE AVI.ID_MARCADOR=MAR.ID AND AVI.N_ESTADO = 1   AND :ID_CAPA_CATEGORIA  AND  ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type"
					+ " (:LONGITUD,:LATITUD ,NULL),NULL,NULL), "
					+ " MAR.CLB_COORDENADA_PUNTO,0.0001,'unit=M'))) <= :RADIO"
					+ " AND ROWNUM <= 100 ORDER BY t.X,t.Y  ASC ";

			parametros.put(":ID_CAPA_CATEGORIA", TipoNivelCapa.CAPA.toString().equals(nivelCapa)
					? " MAR.ID_CAPA =" + idCapaCategoria + "" : " MAR.ID_CATEGORIA = " + idCapaCategoria + "");
			parametros.put(":LATITUD", latitud);
			parametros.put(":LONGITUD", longitud);
			parametros.put(":RADIO", radioAccion);
			
			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), (rs, rowNum) ->
					new MarkerPoint(rs.getLong(1),
							new Point(rs.getDouble(3), rs.getDouble(2)),
							urlIconos+rs.getString(4)));
		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa y radio accion {}{}--",
					(" id capa :" + idCapaCategoria + " radio accion :" + radioAccion), e);
			throw new SQLException("Error al obtener los avistamientos por capa y radio accion {}{}--", e);
		}
	}

	@Override
	public List<MarkerPoint> obtenerMarcadorPorCapaYNivelCapa(String nivelCapa, Long idCapaCategoria)
			throws SQLException {
		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			//SQL = " SELECT MAR.ID, MAR.CLB_COORDENADA_PUNTO.SDO_POINT.X AS X, MAR.CLB_COORDENADA_PUNTO.SDO_POINT.Y AS Y, "
			SQL = " SELECT MAR.ID, t.X AS X, t.Y AS Y,"
					+ " ICO.S_RUTA_LOGO AS RUTA_LOGO FROM CONTENEDOR.D247CON_MARCADOR MAR, "
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t,"
					+ " CONTENEDOR.D247CON_ICONO ICO, " + " AVISTAM.T247AVI_AVISTAMIENTO AVI"
					+ " WHERE AVI.ID_MARCADOR=MAR.ID AND MAR.ID_ICONO= ICO.ID AND AVI.N_ESTADO = 1 AND :ID_CAPA_CATEGORIA ";

			parametros.put(":ID_CAPA_CATEGORIA", TipoNivelCapa.CAPA.toString().equals(nivelCapa)
					? " MAR.ID_CAPA =" + idCapaCategoria + "" : " MAR.ID_CATEGORIA = " + idCapaCategoria + "");

			getJdbcTemplate().setFetchSize(1000);

			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), (rs, rowNum) ->
					new MarkerPoint(rs.getLong(1),
							new Point(rs.getDouble(3), rs.getDouble(2)),
							urlIconos+rs.getString(4)));
		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa y radio accion {}{}--",
					(" id capa :" + idCapaCategoria), e);
			throw new SQLException("Error al obtener los avistamientos por capa y radio accion {}{}--", e);
		}
	}

	@Override
	public List<MarkerPoint> obtenerMarcadoresPorIdCapa(Long idCapa) throws SQLException {
		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			SQL = " SELECT MAR.ID, t.X AS X, t.Y AS Y,"
					+ " ICO.S_RUTA_LOGO AS RUTA_LOGO FROM CONTENEDOR.D247CON_MARCADOR MAR, "
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t, CONTENEDOR.D247CON_ICONO ICO "
					+ " WHERE MAR.ID_ICONO= ICO.ID AND MAR.ID_CAPA = :ID_CAPA";

			// SQL = " SELECT t.X, t.Y, MAR.ID, ICO.S_RUTA_LOGO AS RUTA_LOGO
			// FROM CONTENEDOR.D247CON_MARCADOR MAR, "
			// + "TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t,
			// CONTENEDOR.D247CON_ICONO ICO "
			// + " WHERE MAR.ID_ICONO= ICO.ID AND MAR.ID_CAPA = :ID_CAPA";

			parametros.put(":ID_CAPA", idCapa);
			getJdbcTemplate().setFetchSize(1000);
			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), (rs, rowNum) ->
					new MarkerPoint(rs.getLong(1),
							new Point(rs.getDouble(3), rs.getDouble(2)),
							urlIconos+rs.getString(4)));
		} catch (Exception e) {
			LOGGER.error("Error al obtener los avistamientos por capa{}{}--", (" id capa :" + idCapa), e);
			throw new SQLException("Error al obtener los avistamientos por capa{}{}--", e);
		}
	}

	@Override
	public List<MarkerPoint> obtenerMarcadoresPorIdUsuario(Long idUsuario) throws SQLException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			SQL = " SELECT MAR.ID, t.X AS X, t.Y AS Y, "
					+ " MAR.ID_ICONO AS RUTA_LOGO FROM CONTENEDOR.D247CON_MARCADOR MAR,"
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t,"
					+ "  AVISTAM.T247AVI_AVISTAMIENTO AVI "
					+ "  WHERE AVI.ID_MARCADOR=MAR.ID "
					+ " AND AVI.N_ESTADO <> 0 AND AVI.ID_USUARIO = :ID_USUARIO ";

			parametros.put(":ID_USUARIO", idUsuario);
			
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
	public List<CategoryRelationship> getPolygonsByLatLng(double lat, double lng, List<Long> idsCategoria)
			throws SQLException {
		SQL = "SELECT R.CATEGORIA, R.NOMBRE, R.DESCRIPCION " + " FROM ("
				+ "SELECT SDO_GEOM.RELATE(M.CLB_COORDENADA_POLYGON,'determine', "
				+ "            MDSYS.SDO_GEOMETRY(2001, 8307, "
				+ "                  MDSYS.SDO_POINT_TYPE(:LNG, :LAT, NULL), "
				+ "                  NULL, NULL), 0.005) RELATIONSHIP, "
				+ " M.CATEGORIA, M.S_NOMBRE NOMBRE, M.S_DESCRIPCION DESCRIPCION "
				+ "FROM (SELECT 	C.S_NOMBRE CATEGORIA, M.S_NOMBRE, M.S_DESCRIPCION,M.CLB_COORDENADA_POLYGON "
				+ "       FROM D247CON_MARCADOR M " + "       INNER JOIN D247CON_CATEGORIA C on C.ID = M.ID_CATEGORIA "
				+ "       WHERE C.ID IN(:IDS_CATEGORIA) " + "       AND M.N_POLIGONO = 1)M)R "
				+ "       WHERE R.RELATIONSHIP <> 'DISJOINT'";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("LNG", lng);
			params.put("LAT", lat);
			params.put("IDS_CATEGORIA", idsCategoria);

			return getNamedParameterJdbcTemplate().query(SQL, params,
					(rs, rowNum) -> new CategoryRelationship(
							rs.getString("CATEGORIA"), rs.getString("NOMBRE"), rs.getString("DESCRIPCION")));

		} catch (Exception ex) {
			throw new SQLException("Error al obtener las relaciones entre las geometrias de las categorias "
					+ idsCategoria.toString() + " y el punto dado.", ex);
		}
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MarkerPoint> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MarkerPoint buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
