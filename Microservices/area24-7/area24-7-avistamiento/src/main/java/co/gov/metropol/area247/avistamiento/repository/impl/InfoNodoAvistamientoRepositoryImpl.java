package co.gov.metropol.area247.avistamiento.repository.impl;

import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.domain.context.app.InfoNodoAvistamiento;
import co.gov.metropol.area247.avistamiento.repository.InfoNodoAvistamientoRepository;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import co.gov.metropol.area247.jdbc.util.Utils;
import io.reactivex.Observable;

@Repository
public class InfoNodoAvistamientoRepositoryImpl extends Dao implements InfoNodoAvistamientoRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(InfoNodoAvistamientoRepositoryImpl.class);

	private List<InfoNodoAvistamiento> InfoNodoAvistamientoList;
	
	@Value("${media.server.url}")
	private String multimediaServerUrl;

	private String SQL = "";

	@Override
	public List<InfoNodoAvistamiento> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfoNodoAvistamiento buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Observable<List<InfoNodoAvistamiento>> obtenerInformacionDelNodoPorNodoPadre(Long idNodoPadre)
			throws SQLException {

		SQL = "SELECT NOD.ID AS ID_NODO, NOD.S_NOMBRE AS NOMBRE_NODO, "
				+ "NOD.S_NOMBRE_CIENTIFICO AS NOMBRE_CIENTIFICO_NODO, "
				+ "NOD.S_DESCRIPCION AS DESCRIPCION_NODO, MUL.ID AS ID_MULTIMEDIA,"
				+ "(SELECT COUNT(*) AS NUM_HIJOS FROM CCONTROL.T247CCN_NODO_ARBOL NDH WHERE NDH.ID_PADRE = NOD.ID) AS NUM_HIJOS"
				+ " FROM CCONTROL.T247CCN_NODO_ARBOL NOD"
				+ " LEFT JOIN CONTENEDOR.T247CON_MULTIMEDIA MUL ON MUL.ID = NOD.ID_MULTIMEDIA " + " WHERE NOD.ID_PADRE = ?";

		return Observable.create(emitter -> {
			try {
				InfoNodoAvistamientoList = getJdbcTemplate().query(SQL, new Object[] { idNodoPadre },
						(RowMapper<InfoNodoAvistamiento>) (rs, rowNum) -> cargarInformacionDelNodo(rs));
				emitter.onNext(InfoNodoAvistamientoList);
				while (emitter.isDisposed()) {
					break;
				}
			} catch (Exception e) {
				emitter.onError(new SQLException(
						"Error al consultar la lista de nodos (avistamientos) por id nodo padre --{}{} " + idNodoPadre,
						e));
			}
		});
	}

	public InfoNodoAvistamiento cargarInformacionDelNodo(ResultSet rs) {
		try {
			InfoNodoAvistamiento infoNodoAvistamiento = new InfoNodoAvistamiento();
			infoNodoAvistamiento.setId(rs.getLong("ID_NODO"));
			infoNodoAvistamiento.setNombre(rs.getString("NOMBRE_NODO"));
			infoNodoAvistamiento.setNombreCientifico(rs.getString("NOMBRE_CIENTIFICO_NODO"));
			infoNodoAvistamiento.setDescripcion(rs.getString("DESCRIPCION_NODO"));
			infoNodoAvistamiento.setRutaMultimedia(multimediaServerUrl + rs.getLong("ID_MULTIMEDIA"));
			infoNodoAvistamiento.setTieneHijos(!Utils.isZero(rs.getLong("NUM_HIJOS")) ? Boolean.TRUE : Boolean.FALSE);
			return infoNodoAvistamiento;
		} catch (Exception e) {
			LOGGER.error("Error al cargar la informacion la informacion del nodo (avistamiento) --{}{}", e);
			throw new SQLException("Error al cargar la informacion la informacion del nodo (avistamiento) --{}{}", e);
		}
	}

}
