package co.gov.metropol.area247.core.repository.impl;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.core.domain.Icono;
import co.gov.metropol.area247.core.domain.context.web.Aplicacion;
import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.domain.context.web.NodoRaiz;
import co.gov.metropol.area247.core.repository.CapaRepository;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import co.gov.metropol.area247.jdbc.util.Utils;

@Repository
public class CapaRepositoryImpl extends Dao implements CapaRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(CapaRepositoryImpl.class);

	private static String SQL = "";

	private final String EMPTY = "";

	@Value("${iconos.server.url}")
	private String iconoServerUrl;

	@Override
	public List<Capa> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Capa buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Capa> obtenerAplicacionYcapasPorTipoCapas(String tipoCapas, Long idAplicacion) throws SQLException {
		try {
			SQL = "SELECT " + "APL.ID AS ID_APLICACION,"
					+ "APL.S_NOMBRE AS NOMBRE, APL.S_DESCRIPCION AS DESCRIPCION_APLICACION,"
					+ "APL.S_CODIGO_COLOR AS CODIGO_COLOR," + "APL.S_CODIGO_TOGGLE AS CODIGO_TOGGLE,"
					+ "APL.N_ACTIVO AS ACTIVO," + "APL.N_RADIO_ACCION AS RADIO_ACCION,"
					+ "APL.D_ULTIMA_ACTUALIZACION AS ULTIMA_ACTUALIZACION," + "APL.ID_ICONO AS ID_ICONO_APLICACION,"
					+ "CAP.ID AS ID_CAPA, CAP.N_HISTORIA_AVISTAMIENTO AS FLAG, " + "CAP.S_NOMBRE AS NOMBRE_CAPA,"
					+ "TCA.S_NOMBRE AS NOMBRE_TIPO_CAPA," + "CAP.ID_ICONO AS ID_ICONO_CAPA, '' AS ID_NODO_ARBOL"
					+ " FROM CONTENEDOR.D247CON_CAPA CAP "
					+ " INNER JOIN CONTENEDOR.D247CON_APLICACION APL ON APL.ID = CAP.ID_APLICACION "
					+ " INNER JOIN CONTENEDOR.D247CON_TIPO_CAPA TCA ON TCA.ID = CAP.ID_TIPO_CAPA "
					+ " :TIPOS  :APLICACION ORDER BY APL.ID ASC ";

			Map<String, Object> params = new HashMap<>();
			params.put(":TIPOS", EMPTY);
			params.put(":APLICACION", EMPTY);

			if (!Utils.isNull.apply(tipoCapas) && !Utils.isZero(idAplicacion)) {
				params.put(":TIPOS", " WHERE TCA.ID IN (" + tipoCapas + ")");
				params.put(":APLICACION", " AND APL.ID =" + idAplicacion);
			} else if (!Utils.isNull.apply(tipoCapas)) {
				params.put(":TIPOS", " WHERE TCA.ID IN (" + tipoCapas + ")");
			} else if (!Utils.isZero(idAplicacion)) {
				params.put(":APLICACION", " WHERE APL.ID =" + idAplicacion);
			}

			return getNamedParameterJdbcTemplate().query(SQL(SQL, params), (RowMapper<Capa>) (rs, rowNum) -> {
				return cargarCapa(rs);
			});
		} catch (Exception e) {
			LOGGER.error("Error al obtenes las aplicaciones y sus capas por tipo capa --{}{}", e);
			throw new SQLException("Error al obtenes las aplicaciones y sus capas por tipo capa --{}{}", e);
		}
	}


	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Capa cargarCapa(ResultSet rs) {
		try {
			Capa capa = new Capa();
			capa.setId(rs.getLong("ID_CAPA"));
			capa.setNodoRaiz(new NodoRaiz());
			capa.getNodoRaiz().setId(rs.getLong("ID_NODO_ARBOL"));
			capa.setNombre(rs.getString("NOMBRE_CAPA"));
			capa.setNombreTipoCapa(rs.getString("NOMBRE_TIPO_CAPA"));
			capa.setRutaIconoCapa(iconoServerUrl + rs.getString("ID_ICONO_CAPA"));
			if(rs.getString("FLAG")==null)
				capa.setContieneHistoria(Boolean.FALSE);
			else
				capa.setContieneHistoria(Utils.isActive(rs.getString("FLAG")) ? Boolean.TRUE : Boolean.FALSE);
			capa.setAplicacion(new Aplicacion());
			capa.getAplicacion().setId(rs.getLong("ID_APLICACION"));
			capa.getAplicacion().setNombre(rs.getString("NOMBRE"));
			capa.getAplicacion().setDescripcion(rs.getString("DESCRIPCION_APLICACION"));
			if(rs.getString("ACTIVO")==null)
				capa.getAplicacion().setActivo(Boolean.FALSE);
			else
				capa.getAplicacion().setActivo(Utils.isActive(rs.getString("ACTIVO")) ? Boolean.TRUE : Boolean.FALSE);
			capa.getAplicacion().setCodigoColor(rs.getString("CODIGO_COLOR"));
			capa.getAplicacion().setCodigoToggle(rs.getString("CODIGO_TOGGLE"));
			capa.getAplicacion().setRadioAccion(rs.getInt("RADIO_ACCION"));
			capa.getAplicacion().setIcono(new Icono());
			capa.getAplicacion().getIcono().setRutaLogo(iconoServerUrl + rs.getString("ID_ICONO_APLICACION"));
			return capa;
		} catch (Exception e) {
			LOGGER.error("Error al cargar el objeto capa --{}{}", e);
			throw new SQLException("Error al cargar el objeto capa --{}{}", e);
		}
	}

}
