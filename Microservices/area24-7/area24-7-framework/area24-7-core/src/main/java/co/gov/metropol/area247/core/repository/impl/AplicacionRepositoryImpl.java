package co.gov.metropol.area247.core.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.repository.AplicacionRepository;
import co.gov.metropol.area247.core.repository.rowmapper.AplicacionRowMapper;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

@Repository
public class AplicacionRepositoryImpl extends Dao implements AplicacionRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(AplicacionRepositoryImpl.class);

	private static String SQL = "";

	@Override
	public List<Aplicacion> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Aplicacion buscarPorId(Long id) throws SQLException {
		try {
			SQL = " SELECT " + "APL.ID AS ID_APLICACION," + "APL.S_NOMBRE AS NOMBRE_APLICACION,"
					+ " APL.S_DESCRIPCION AS DESCRIPCION_APLICACION," + "APL.S_CODIGO_COLOR AS CODIGO_COLOR,"
					+ " APL.S_CODIGO_TOGGLE AS CODIGO_TOGGLE," + "APL.N_ACTIVO AS ACTIVO,"
					+ " APL.N_RADIO_ACCION AS RADIO_ACCION," + "ICO.ID AS ID_ICONO," + "ICO.S_NOMBRE AS NOMBRE_ICONO,"
					+ " ICO.S_RUTA_LOGO AS RUTA_ICONO" + " FROM CONTENEDOR.D247CON_APLICACION APL "
					+ " INNER JOIN CONTENEDOR.D247CON_ICONO ICO ON ICO.ID = APL.ID_ICONO" + " WHERE APL.ID = ? ";

			return getJdbcTemplate().queryForObject(SQL, new Object[] { id }, new AplicacionRowMapper());
		} catch (Exception e) {
			LOGGER.error("Error al obtener la aplicacion por su id --{}{}", id, e);
			throw new SQLException("Error al obtener la aplicacion por su id --{}{}", e);
		}

	}
	
	@Override
	public Aplicacion consultarAplicacionPorIdCapa(Long idCapa) throws SQLException {
		try {
			SQL = " SELECT " + "APL.ID AS ID_APLICACION," + "APL.S_NOMBRE AS NOMBRE_APLICACION,"
					+ " APL.S_DESCRIPCION AS DESCRIPCION_APLICACION," + "APL.S_CODIGO_COLOR AS CODIGO_COLOR,"
					+ " APL.S_CODIGO_TOGGLE AS CODIGO_TOGGLE," + "APL.N_ACTIVO AS ACTIVO,"
					+ " APL.N_RADIO_ACCION AS RADIO_ACCION," + "ICO.ID AS ID_ICONO," + "ICO.S_NOMBRE AS NOMBRE_ICONO,"
					+ " ICO.S_RUTA_LOGO AS RUTA_ICONO" + " FROM CONTENEDOR.D247CON_APLICACION APL "
					+ " INNER JOIN CONTENEDOR.D247CON_CAPA CAP ON CAP.ID_APLICACION = APL.ID"
					+ " INNER JOIN CONTENEDOR.D247CON_ICONO ICO ON ICO.ID = APL.ID_ICONO" 
					+ " WHERE CAP.ID = ? ";

			return getJdbcTemplate().queryForObject(SQL, new Object[] { idCapa }, new AplicacionRowMapper());
		} catch (Exception e) {
			LOGGER.error("Error al obtener la aplicacion por  id capa --{}{}", idCapa, e);
			throw new SQLException("Error al obtener la aplicacion por  id capa --{}{}", e);
		}
	}


	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
