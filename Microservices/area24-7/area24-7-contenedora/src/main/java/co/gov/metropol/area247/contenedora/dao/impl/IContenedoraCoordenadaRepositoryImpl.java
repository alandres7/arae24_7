package co.gov.metropol.area247.contenedora.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCoordenadaRepositoryCustom;
import co.gov.metropol.area247.contenedora.model.Coordenada;

@Repository("coordenadaDao")
@Transactional
public class IContenedoraCoordenadaRepositoryImpl implements IContenedoraCoordenadaRepositoryCustom {

	private final Logger LOGGER = LoggerFactory.getLogger(IContenedoraCoordenadaRepositoryImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> obtenerMarcadorPoligonosPorRadioAccion(Long idMarcador, double latitud, double Longitud,
			int radio) {
		List<Coordenada> resultado = new ArrayList<Coordenada>();
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT t247con_coordenada.* "+
					"FROM d247con_marcador " +
					"INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id " +
					"WHERE d247con_marcador.id = " + idMarcador +
					" and   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
					+ Longitud + ", " + latitud + ",NULL),NULL,NULL),"
					+ "t247con_coordenada.clb_coordenada_polygon,0.0001,'unit=KM') ) ) < " + radio)
					.addEntity(Coordenada.class);

			//LOGGER.info("transaccion recuperada " + query.uniqueResult());
			//query.list().forEach(action -> LOGGER.info("resultado transaccion ---> " + action));
			resultado = (List<Coordenada>) query.list();
		} catch (Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
		}

		return resultado == null ? new ArrayList<Coordenada>() : resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> obtenerPoligonosPorRadioAccionSubcategoria(Long idSubcategoria, double latitud,
			double Longitud, double radio) {
		List<Coordenada> resultado = new ArrayList<Coordenada>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT t247con_coordenada.* "
							+ "FROM d247con_marcador INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id "
							+ "INNER JOIN d247con_subcategoria_marcador ON d247con_subcategoria_marcador.id_marcador = d247con_marcador.id "
							+ "INNER JOIN d247con_subcategoria ON d247con_subcategoria.id = d247con_subcategoria_marcador.id_subcategoria "
							+ "INNER JOIN d247con_categoria_subcategoria ON d247con_categoria_subcategoria.id_subcategoria = d247con_subcategoria.id "
							+ "INNER JOIN d247con_categoria ON d247con_categoria_subcategoria.id_categoria = d247con_categoria.id "
							+ " WHERE " + " d247con_categoria_subcategoria.id = " + idSubcategoria
							+ " AND   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
							+ Longitud + ", " + latitud + ",NULL),NULL,NULL),"
							+ "t247con_coordenada.clb_coordenada_polygon,0.0001,'unit=KM') ) ) < " + radio)
					.addEntity(Coordenada.class);
//			LOGGER.info("transaccion recuperada " + query.uniqueResult());
//			query.list().forEach(action -> LOGGER.info("resultado transaccion ---> " + action));
			resultado = (List<Coordenada>) query.list();
		} catch (Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
		}

		return resultado == null ? null : resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> obtenerPoligonosPorRadioAccionCategoria(Long idCategoria, double latitud,
			double Longitud, double radio) {
		
		List<Coordenada> resultado = new ArrayList<Coordenada>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT t247con_coordenada.* "+
						"FROM d247con_marcador INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id "+
						"INNER JOIN d247con_categoria_marcador ON d247con_categoria_marcador.id_marcador = d247con_marcador.id " +
						"INNER JOIN d247con_categoria ON d247con_categoria.id = d247con_categoria_marcador.id_categoria "+
						" WHERE d247con_categoria.id = "+ idCategoria +
						" AND   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
						+ Longitud + ", " + latitud + ",NULL),NULL,NULL),"
						+ "t247con_coordenada.clb_coordenada_polygon,0.0001,'unit=KM') ) ) < " + radio)
					.addEntity(Coordenada.class);
//			LOGGER.info("transaccion recuperada " + query.uniqueResult());
//			query.list().forEach(action -> LOGGER.info("resultado transaccion ---> " + action));
			resultado = (List<Coordenada>) query.list();
		} catch (Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
		}

		return resultado == null ? null : resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> obtenerPuntosPorRadioAccionCategoria(Long idCategoria, double latitud,
			double Longitud, double radio) {
		
		List<Coordenada> resultado = new ArrayList<Coordenada>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT t247con_coordenada.* "+
						"FROM d247con_marcador INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id "+
						"INNER JOIN d247con_categoria_marcador ON d247con_categoria_marcador.id_marcador = d247con_marcador.id " +
						"INNER JOIN d247con_categoria ON d247con_categoria.id = d247con_categoria_marcador.id_categoria "+
						" WHERE d247con_categoria.id = "+ idCategoria +
						" AND   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
						+ Longitud + ", " + latitud + ",NULL),NULL,NULL),"
						+ "t247con_coordenada.clb_coordenada_punto,0.0001,'unit=KM') ) ) < " + radio)
					.addEntity(Coordenada.class);
//			LOGGER.info("transaccion recuperada " + query.uniqueResult());
//			query.list().forEach(action -> LOGGER.info("resultado transaccion ---> " + action));
			resultado = (List<Coordenada>) query.list();
		} catch (Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
		}

		return resultado == null ? null : resultado;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> obtenerPoligonosPorRadioAccionCapa(Long idCapa, double latitud,
			double Longitud, double radio) {
		
		List<Coordenada> resultado = new ArrayList<Coordenada>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT t247con_coordenada.* "+
						"FROM d247con_marcador INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id "+
						"INNER JOIN d247con_capa_marcador ON d247con_capa_marcador.id_marcador = d247con_marcador.id " +
						"INNER JOIN d247con_capa ON d247con_capa.id = d247con_capa_marcador.id_capa "+
						" WHERE d247con_capa.id = "+ idCapa +
						" AND   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
						+ Longitud + ", " + latitud + ",NULL),NULL,NULL),"
						+ "t247con_coordenada.clb_coordenada_polygon,0.0001,'unit=KM') ) ) < " + radio)
					.addEntity(Coordenada.class);
			//LOGGER.info("transaccion recuperada " + query.uniqueResult());
			//query.list().forEach(action -> LOGGER.info("resultado transaccion ---> " + action));
			resultado = (List<Coordenada>) query.list();
		} catch (Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
		}

		return resultado == null ? null : resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> obtenerPuntosPorRadioAccionCapa(Long idCapa, double latitud,
			double Longitud, double radio) {
		
		List<Coordenada> resultado = new ArrayList<Coordenada>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT t247con_coordenada.* "+
						"FROM d247con_marcador INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id "+
						"INNER JOIN d247con_capa_marcador ON d247con_capa_marcador.id_marcador = d247con_marcador.id " +
						"INNER JOIN d247con_capa ON d247con_capa.id = d247con_capa_marcador.id_capa "+
						" WHERE d247con_capa.id = "+ idCapa +
						" AND   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
						+ Longitud + ", " + latitud + ",NULL),NULL,NULL),"
						+ "t247con_coordenada.clb_coordenada_punto,0.0001,'unit=KM') ) ) < " + radio)
					.addEntity(Coordenada.class);
			//LOGGER.info("transaccion recuperada " + query.uniqueResult());
			//query.list().forEach(action -> LOGGER.info("resultado transaccion ---> " + action));
			resultado = (List<Coordenada>) query.list();
		} catch (Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
		}

		return resultado == null ? null : resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coordenada> coordenadaGeometryPolygonIntercepto(Long idCategoria, double lat, double lng){
		List<Coordenada> coordenadas;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("select co.* " + 
							"    FROM T247CON_COORDENADA co "+ 
							"    inner join d247con_marcador m on m.id =  co.id_marcador " + 
							"    inner join d247con_categoria_marcador cm on m.id = cm.id_marcador " + 
							"    inner join d247con_categoria c on c.id = cm.id_categoria " +
							"    where c.id ="+idCategoria+
							"          AND  SDO_UTIL.GETNUMVERTICES(" + 
							"               SDO_GEOM.SDO_INTERSECTION(co.CLB_COORDENADA_POLYGON," + 
							"               MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE("+lng+", "+lat+", NULL), NULL, NULL), 0.005)) > 0")
					.addEntity(Coordenada.class);
			
			coordenadas = (List<Coordenada>) query.list();
			
		}catch(Exception e) {
			LOGGER.error("error en transaccion " + e.getMessage() + ", causa: " + e.getCause());
			coordenadas = new ArrayList<>();
		}
		return coordenadas;
	}
	
	
}
