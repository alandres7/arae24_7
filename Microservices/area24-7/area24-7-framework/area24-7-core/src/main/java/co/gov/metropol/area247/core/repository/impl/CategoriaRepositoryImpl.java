package co.gov.metropol.area247.core.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.domain.context.web.Categoria;
import co.gov.metropol.area247.core.domain.context.web.NodoRaiz;
import co.gov.metropol.area247.core.repository.CategoriaRepository;
import co.gov.metropol.area247.core.repository.rowmapper.CategoriaRowMapper;
import co.gov.metropol.area247.jdbc.dao.Dao;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import co.gov.metropol.area247.jdbc.util.Utils;

@Repository
public class CategoriaRepositoryImpl extends Dao implements CategoriaRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(CategoriaRepositoryImpl.class);

	@Value("${iconos.server.url}")
	private String iconoServerUrl;

	private String SQL = "";

	@Override
	protected String getSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categoria> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria buscarPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categoria> obtenerCategoriasPorTipoCapasOrTipoCategoria(String tipoCategorias, String tipoCapas,
			Long idAplicacion) throws SQLException {
		try {
			SQL = " SELECT " + " CAP.ID AS ID_CAPA," + "CAP.S_NOMBRE AS NOMBRE_CAPA," + "CAT.ID AS ID_CATEGORIA,"
					+ "CAT.S_NOMBRE AS NOMBRE_CATEGORIA " + " FROM CONTENEDOR.D247CON_CAPA CAP"
					+ " LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON CAT.ID_CAPA= CAP.ID WHERE :FILTER_TIPO_CATEGORIAS :FILTER_TIPO_CAPAS"
					+ " :FILTER_APLICACION";
			Map<String, Object> parametros = new HashMap<>();
			parametros.put(":FILTER_TIPO_CATEGORIAS", "");
			parametros.put(":FILTER_TIPO_CAPAS", "");
			parametros.put(":FILTER_APLICACION", "");
			if (!Utils.isNull.apply(tipoCategorias)) {
				parametros.put(":FILTER_TIPO_CATEGORIAS", " CAT.ID_TIPO_CATEGORIA IN (" + tipoCategorias + ")");
				if (!Utils.isNull.apply(tipoCapas)) {
					parametros.put(":FILTER_TIPO_CAPAS", " AND CAP.ID_TIPO_CAPA IN (" + tipoCapas + ")");
				}
				if (!Utils.isZero(idAplicacion)) {
					parametros.put(":FILTER_APLICACION", " AND CAP.ID_APLICACION =" + idAplicacion);
				}
			} else if (!Utils.isNull.apply(tipoCapas)) {
				parametros.put(":FILTER_TIPO_CAPAS", " CAP.ID_TIPO_CAPA IN (" + tipoCapas + ")");
				if (!Utils.isZero(idAplicacion)) {
					parametros.put(":FILTER_APLICACION", " AND CAP.ID_APLICACION =" + idAplicacion);
				}
			} else if (!Utils.isZero(idAplicacion)) {
				parametros.put(":FILTER_APLICACION", " CAP.ID_APLICACION =" + idAplicacion);
			} else {
				SQL = SQL.replace("WHERE", " ");
			}

			return getNamedParameterJdbcTemplate().query(SQL(SQL, parametros), new CategoriaRowMapper());
		} catch (Exception e) {
			LOGGER.error("Error al consultas las por tipoCategorias o tipoCapas o aplicacion --{}{}",
					("tipo tecegorias" + tipoCategorias + "tipo capas " + tipoCapas + " aplicacion " + idAplicacion),
					e);
			throw new SQLException("Error al consultas las por tipoCategorias o tipoCapas o aplicacion ", e);
		}
	}

	@Override
	public List<Categoria> obtenerCategoriasPorIdAplicacion(Long idAplicacion) throws SQLException {
		try {
			SQL = "SELECT CAP.ID AS ID_CAPA, CAP.N_HISTORIA_AVISTAMIENTO AS FLAG, CAP.S_NOMBRE AS NOMBRE_CAPA, "
					+ "CAP.ID_ICONO AS ID_ICONO_CAPA, NAR.ID AS ID_NODO_ARBOL,"
					+ "NAR.ID AS ID_NODO_ARBOL, NAR.S_NOMBRE AS PREGUNTA_NODO, NAR.S_DESCRIPCION AS DESCRIPCION_NODO,"
					+ "(SELECT COUNT(*) AS NUM_HIJOS FROM CCONTROL.T247CCN_NODO_ARBOL NDH WHERE NDH.ID_PADRE = NAR.ID) AS NUM_HIJOS, "
					+ "MUL.S_RUTA AS URL_MULTIMEDIA, CAT.ID AS ID_CATEGORIA, CAT.S_NOMBRE AS NOMBRE_CATEGORIA"
					+ " FROM CONTENEDOR.D247CON_CAPA CAP "
					+ " LEFT JOIN CONTENEDOR.D247CON_TIPO_CAPA TCA ON TCA.ID = CAP.ID_TIPO_CAPA "
					+ " LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON CAT.ID_CAPA = CAP.ID "
					+ " LEFT JOIN CCONTROL.T247CCN_ARBOL_DECISION ADS ON ADS.ID_CAPA = CAP.ID "
					+ " LEFT JOIN CCONTROL.T247CCN_NODO_ARBOL NAR ON NAR.ID_ARBOL = ADS.ID "
					+ " LEFT JOIN CONTENEDOR.T247CON_MULTIMEDIA MUL ON MUL.ID = NAR.ID_MULTIMEDIA "
					+ " WHERE (TCA.S_NOMBRE IN('Subcapas', 'Avistamiento'))  AND CAP.ID_APLICACION=:ID_APLICACION  AND NAR.ID_PADRE IS NULL";

			Map<String, Object> params = new HashMap<>();
			params.put(":ID_APLICACION", idAplicacion);

			return getNamedParameterJdbcTemplate().query(SQL(SQL, params), (RowMapper<Categoria>) (rs, rowNum) -> {
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				categoria.setNombre(rs.getString("NOMBRE_CATEGORIA"));
				categoria.setCapa(new Capa());
				categoria.getCapa().setId(rs.getLong("ID_CAPA"));
				categoria.getCapa().setNombre(rs.getString("NOMBRE_CAPA"));
				categoria.getCapa()
						.setContieneHistoria(Utils.isActive(rs.getString("FLAG")) ? Boolean.TRUE : Boolean.FALSE);
				categoria.getCapa().setRutaIconoCapa(iconoServerUrl + rs.getString("ID_ICONO_CAPA"));
				categoria.getCapa().setNodoRaiz(new NodoRaiz());
				categoria.getCapa().getNodoRaiz().setId(rs.getLong("ID_NODO_ARBOL"));
				categoria.getCapa().getNodoRaiz().setPregunta(rs.getString("PREGUNTA_NODO"));
				categoria.getCapa().getNodoRaiz().setDescripcion(rs.getString("DESCRIPCION_NODO"));
				categoria.getCapa().getNodoRaiz().setRutaMultimedia(rs.getString("URL_MULTIMEDIA"));
				categoria.getCapa().getNodoRaiz()
						.setTieneHijos(!Utils.isZero(rs.getLong("NUM_HIJOS")) ? Boolean.TRUE : Boolean.FALSE);
				return categoria;
			});
		} catch (Exception e) {
			LOGGER.error("Error al obtenes las aplicaciones y sus capas por tipo capa --{}{}", e);
			throw new SQLException("Error al obtenes las aplicaciones y sus capas por tipo capa --{}{}", e);
		}
	}

	@Override
	public List<Categoria> obtenerCategoriaPorCapaYTipoCategorias(Long idCapa, String tipoCategorias) {
		try {
			SQL = " SELECT CAP.ID AS ID_CAPA, CAP.S_NOMBRE AS NOMBRE_CAPA,"
					+ " CAT.ID AS ID_CATEGORIA, CAT.S_NOMBRE AS NOMBRE_CATEGORIA"
					+ " FROM CONTENEDOR.D247CON_CAPA CAP "
					+ " LEFT JOIN CONTENEDOR.D247CON_CATEGORIA CAT ON CAT.ID_CAPA = CAP.ID"
					+ " LEFT JOIN CONTENEDOR.D247CON_TIPO_CAPA TCA ON TCA.ID = CAT.ID_TIPO_CATEGORIA"
					+ " WHERE CAP.ID =:ID_CAPA AND (TCA.ID IN(:TIPO_CATEGORIAS))";

			Map<String, Object> params = new HashMap<>();

			params.put(":ID_CAPA", idCapa);
			params.put(":TIPO_CATEGORIAS", tipoCategorias);

			return getNamedParameterJdbcTemplate().query(SQL(SQL, params), new CategoriaRowMapper());
		} catch (Exception e) {
			LOGGER.error("Error al obtenes las categorias por capa --{}{}", idCapa, e);
			throw new SQLException("Error al obtenes las categorias por capa --{}{}", e);
		}
	}

}
