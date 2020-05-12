package co.gov.metropol.area247.covid19.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.covid19.dao.ILayerDaoJdbc;
import co.gov.metropol.area247.covid19.dto.LayerDto;
import co.gov.metropol.area247.covid19.jdbc.Dao;
import co.gov.metropol.area247.covid19.jdbc.SQLException;

@Repository
public class ILayerDaoJdbcImpl extends Dao implements ILayerDaoJdbc {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ILayerDaoJdbcImpl.class);

	private String SQL;

	@Override
	public List<LayerDto> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LayerDto buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void borrarMarcadoresXIdCapa( long idCapa ) {
		
		try {
			SQL = "DELETE FROM CONTENEDOR.D247CON_MARCADOR WHERE ID_CATEGORIA IN "
					+ "( SELECT ID FROM CONTENEDOR.D247CON_CATEGORIA WHERE ID_CAPA = :ID_CAPA )";
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ID_CAPA", idCapa);
			
			getNamedParameterJdbcTemplate().update(SQL, parametros);
			
		}catch (Exception e) {
			LOGGER.error("Error al eliminar los marcadores por id de capa  {}{}", idCapa, e);
			throw new SQLException("Error al eliminar los marcadores por id de capa ", e);
		}
		
	}
	
	

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
