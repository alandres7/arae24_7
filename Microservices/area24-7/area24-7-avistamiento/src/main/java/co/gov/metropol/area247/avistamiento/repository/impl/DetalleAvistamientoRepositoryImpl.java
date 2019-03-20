package co.gov.metropol.area247.avistamiento.repository.impl;

import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.domain.context.web.DetalleAvistamiento;
import co.gov.metropol.area247.avistamiento.repository.DetalleAvistamientoRepository;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.core.domain.marker.dto.Point;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
public class DetalleAvistamientoRepositoryImpl extends Dao implements DetalleAvistamientoRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(DetalleAvistamientoRepositoryImpl.class);

	private static String SQL = "";

	@Override
	public List<DetalleAvistamiento> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DetalleAvistamiento buscarPorId(Long id) throws SQLException {
		try {
			SQL = " SELECT t.X, t.Y, AVI.ID, ICO.ID AS ID_ICONO,ICO.S_NOMBRE AS NOMBRE_ICONO, "
					+ " MAR.ID_CAPA, MAR.ID_CATEGORIA, AVI.ID AS ID_AVISTAMIENTO ,AVI.S_NOMBRE_COMUN AS NOMBRE_AVISTAMIENTO, AVI.S_DESCRIPCION AS DESCRIPCION_AVISTAMIENTO, "
					+ " (CASE WHEN MAR.ID_CAPA IS NOT NULL THEN"
					+ " (SELECT CAP.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CAPA CAP WHERE CAP.ID = MAR.ID_CAPA)"
					+ " ELSE (SELECT CAT.N_HISTORIA_AVISTAMIENTO FROM CONTENEDOR.D247CON_CATEGORIA CAT WHERE CAT.ID = MAR.ID_CATEGORIA) END)"
					+ " AS TIENE_HISTORIA" + " FROM CONTENEDOR.D247CON_MARCADOR MAR, "
					+ " TABLE(SDO_UTIL.GETVERTICES(MAR.CLB_COORDENADA_PUNTO)) t, CONTENEDOR.D247CON_ICONO ICO,"
					+ " AVISTAM.T247AVI_AVISTAMIENTO AVI" + " WHERE AVI.ID_MARCADOR=MAR.ID AND MAR.ID_ICONO= ICO.ID"
					+ " AND AVI.ID = ? ";
			return getJdbcTemplate().queryForObject(SQL, new Object[] { id },
					(RowMapper<DetalleAvistamiento>) (rs, rowNum) -> {
						return cargarDetalleAvistamiento(rs);
					});
		} catch (Exception e) {
			LOGGER.error("Error al obtener el detalle avistamiento por id --{}{}", id, e);
			return null;
		}
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private DetalleAvistamiento cargarDetalleAvistamiento(ResultSet rs) {
		try {
			DetalleAvistamiento detalleAvistamiento = new DetalleAvistamiento();
			detalleAvistamiento.setIdAvistamiento(rs.getLong("ID_AVISTAMIENTO"));
			detalleAvistamiento.setIdCapa(rs.getLong("ID_CAPA"));
			detalleAvistamiento.setIdCategoria(rs.getLong("ID_CATEGORIA"));
			detalleAvistamiento.setNombreAvistamiento(rs.getString("NOMBRE_AVISTAMIENTO"));
			detalleAvistamiento.setDescripcionAvistamiento(rs.getString("DESCRIPCION_AVISTAMIENTO"));
			detalleAvistamiento.setTieneHistoria(rs.getInt("TIENE_HISTORIA"));
			detalleAvistamiento.setPoint(new Point(rs.getFloat("Y"), rs.getFloat("X")));
			//detalleAvistamiento.getPoint().setLat(rs.getDouble("Y"));
			//detalleAvistamiento.getPoint().setLng(rs.getDouble("X"));
			detalleAvistamiento.setImageAvistamiento(new Icono());
			detalleAvistamiento.getImageAvistamiento().setId(rs.getLong("ID_ICONO"));
			detalleAvistamiento.getImageAvistamiento().setNombre(rs.getString("NOMBRE_ICONO"));
			return detalleAvistamiento;
		} catch (Exception e) {
			LOGGER.error("Error al cargar el avistamiento --{}{}", e);
			throw new SQLException("Error al cargar el avistamiento --{}{}", e);
		}
	}
	
	

}
