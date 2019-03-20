package co.gov.metropol.area247.avistamiento.repository.impl;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.repository.MarcadorPuntoAvistamientoRepository;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.domain.marker.dto.Point;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
public class MarcadorPuntoAvistamientoRepositoryImpl extends Dao implements MarcadorPuntoAvistamientoRepository {

	private Logger LOGGER = LoggerFactory.getLogger(MarcadorPuntoAvistamientoRepositoryImpl.class);

	@Value("${iconos.server.url}")
	private String rutaWebIcono;

	private static String SQL = "";

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

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MarkerPoint> obtenerMarcadorAvistamientoPorNombreOCapa(String nombre, double longitud, double latitud)
			throws SQLException {
		try {
			SQL = "SELECT" + " t.X, " + "t.Y," + "MAR.ID," + "ICO.ID AS ID_ICONO" + " FROM "
					+ " AVISTAM.T247AVI_AVISTAMIENTO AVI," + " CONTENEDOR.D247CON_MARCADOR MAR, "
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t," + "CONTENEDOR.D247CON_ICONO ICO "
					+ " WHERE" + " :NOMBRE  AND" + " AVI.ID_MARCADOR = MAR.ID AND ICO.ID = MAR.ID_ICONO AND "
					+ " AVI.N_ESTADO = 1 AND "
					+ "(( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type "
					+ "(:LONGITUD, :LATITUD ,NULL),NULL,NULL),"
					+ "MAR.CLB_COORDENADA_PUNTO,0.0001,'unit=KM'))) < 500  and rownum <= 100";
			Map<String, Object> parametros = new HashMap<>();

			parametros.put(":NOMBRE", " (LOWER(AVI.S_NOMBRE_COMUN) LIKE '%" + nombre.toLowerCase()
					+ "%' OR LOWER(AVI.S_NOMBRE_CIENTIFICO) LIKE '%" + nombre.toLowerCase() + "%') ");

			parametros.put(":LATITUD", latitud);
			parametros.put(":LONGITUD", longitud);

			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros),
					(RowMapper<MarkerPoint>) (rs, rowNum) -> {
						return cargarMarcador(rs);
					});
		} catch (Exception e) {
			LOGGER.error(
					"Error al obtener los avistamientos por nombre avistamiento" + ("nombre :" + nombre) + " capa ", e);
			throw new SQLException("Error al obtener los avistamientos por nombre avistamiento" + ("nombre :" + nombre),
					e);
		}
	}

	public MarkerPoint cargarMarcador(ResultSet rs) {
		try {
			MarkerPoint markerPoint = new MarkerPoint();
			markerPoint.setIdMarker(rs.getLong("ID"));
			markerPoint.setPoint(new Point(rs.getFloat("Y"), rs.getFloat("X")));
			markerPoint.setRutaWebIcono(rutaWebIcono + rs.getString("ID_ICONO"));
			return markerPoint;
		} catch (Exception e) {
			LOGGER.error("Error al cargar el marcador {}{}--", e);
			throw new SQLException("rror al cargar el marcador {}{}--", e);
		}
	}

}
