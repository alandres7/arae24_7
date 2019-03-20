package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Coordenada;
@Deprecated
@Repository
public interface IContenedoraCoordenadaRepository
		extends CrudRepository<Coordenada, Long>, IContenedoraCoordenadaRepositoryCustom {

	Coordenada findByLatitudAndLongitud(float latitud, float longitud);

	@Query(name = "coordenadaPuntoRadioAccionDistance", value = "\r\n" + "SELECT x FROM Coordenada x \r\n"
			+ "where  SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 x.coordenadaPunto, \r\n"
			+ "                                 0.0005 ,'unit=KM') < (:radius)"
			+ "                                 AND x.id = (:id)")
	List<Coordenada> coordenadaPuntoRadioAccionDistance(@Param("latitude") final double latitude,
			@Param("longitude") final double longitude, @Param("radius") final float radius,
			@Param("id") final Long id);

	@Query(name = "coordenadaPolygonRadioAccionDistance", value = "\r\n" + "SELECT x FROM Coordenada x \r\n"
			+ "where  SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 x.coordenadaPolygon, \r\n"
			+ "                                 0.0005 ,'unit=KM') < (:radius)"
			+ "                                 AND x.id = (:id)")
	List<Coordenada> coordenadaPolygonRadioAccionDistance(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud, @Param("radius") final float radio, @Param("id") final Long id);

	@Query(name = "coordenadaGeometryPolygonRelate", value = "\r\n" + "SELECT x FROM Coordenada x \r\n"
			+ "where SDO_RELATE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 x.coordenadaPolygon, \r\n"
			+ "                                 'mask=touch+coveredby') = 'TRUE' "
			+ "                                 AND x.id = (:id)")
	Coordenada coordenadaGeometryPolygonRelate(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud, @Param("id") final Long id);

	@Query(name = "coordenadaGeometryPolygonInside", value = "\r\n" + "SELECT x FROM Coordenada x \r\n"
			+ "where SDO_INSIDE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 x.coordenadaPolygon) = 'TRUE' "
			+ "                                 AND x.id = (:id)")
	Coordenada coordenadaGeometryPolygonInside(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud, @Param("id") final Long id);
	
	@Query(name = "coordenadaGeometryPolygonInsideID", value = "\r\n" + "SELECT x FROM Coordenada x \r\n"
			+ "where SDO_INSIDE(x.coordenadaPolygon, MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE(:latitude, :longitude, NULL), NULL, NULL)\r\n"
			+ "                                 ) = 'TRUE' ")
	List<Coordenada> coordenadaGeometryPolygonInside(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud);
	
	@Query(name = "coordenadaGeometryPolygonIntercepto", value = "SELECT x FROM Coordenada x "
			+ "where SDO_UTIL.GETNUMVERTICES(SDO_GEOM.SDO_INTERSECTION(x.coordenadaPolygon, "
			+ " MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE(:longitude, :latitude, NULL), NULL, NULL), 0.005)) > 0 ")
	List<Coordenada> coordenadaGeometryPolygonIntercepto(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud);

	@Query(name = "coordenadaGeometryPolygonCentroide", value = "\r\n" +

			"UPDATE Coordenada x set x.coordenadaPunto =  (SDO_GEOM.SDO_CENTROID(x.coordenadaPolygon, 0.0005)) "
			+ "where  x.id = (:id)")
	Coordenada coordenadaGeometryPolygonCentroide(@Param("id") final Long id);

	@Query(name = "obtenerMarcadorPorRadio", value = "SELECT t247con_coordenada.* " + "FROM d247con_marcador "
			+ "INNER JOIN t247con_coordenada ON t247con_coordenada.id_marcador = d247con_marcador.id "
			+ "WHERE d247con_marcador.id = :idMarcador"
			+ " and   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
			+ " :Longitud, :latitud, NULL),NULL,NULL),"
			+ "t247con_coordenada.clb_coordenada_punto,0.0001,'unit=KM') <  :radio )"
			+ " or "
			+ "( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
			+ "	:Longitud, :latitud, NULL),NULL,NULL), "
			+ "	t247con_coordenada.clb_coordenada_polygon,0.0001,'unit=KM') <  :radio ) )", nativeQuery = true)
	List<Coordenada> obtenerMarcadorPorRadio(@Param("latitud") final double latitud,
			@Param("Longitud") final double longitud, @Param("idMarcador") final Long idMarcador,
			@Param("radio") final int radio);

	@Query(name = "obtenerMarcadorCapaPorRadio", value = "SELECT t247con_coordenada.* " + "FROM d247con_capa "
			+ "INNER JOIN d247con_capa_marcador ON d247con_capa.id = d247con_capa_marcador.id_capa "
			+ "INNER JOIN d247con_marcador ON d247con_marcador.id = d247con_capa_marcador.id_marcador "
			+ "INNER JOIN t247con_coordenada ON d247con_marcador.id = t247con_coordenada.id_marcador "
//			+ "INNER JOIN vigias.t247vig_reporte_vigia ON d247con_marcador.id = vigias.t247vig_reporte_vigia.ID_MARCADOR "
//			+ "INNER JOIN CONTROL.T247CCN_NODO_ARBOL ON CONTROL.T247CCN_NODO_ARBOL.ID = vigias.t247vig_reporte_vigia.ID_NODO_ARBOL "
			//+ "WHERE d247con_capa.id = :idCapa"
			+ "WHERE d247con_marcador.id = :idMarcador"
			+ " and   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
			+ " :Longitud, :latitud, NULL),NULL,NULL),"
			+ "t247con_coordenada.clb_coordenada_punto,0.0001,'unit=KM') ) ) <  :radio", nativeQuery = true)
	List<Coordenada> obtenerMarcadorCapaPorRadio(@Param("latitud") final double latitud,
			@Param("Longitud") final double longitud, @Param("idMarcador") final Long idMarcador,
			@Param("radio") final int radio);

	@Query(name = "obtenerMarcadorValidacionReportePorRadio", value = "SELECT t247con_coordenada.* "
			+ "FROM t247vig_reporte_vigia "
			+ "INNER JOIN d247con_marcador ON d247con_marcador.id = t247vig_reporte_vigia.id_marcador "
			+ "INNER JOIN t247ccn_nodo_arbol ON t247ccn_nodo_arbol.id = t247vig_reporte_vigia.id_nodo_arbol "
			+ "INNER JOIN t247con_coordenada ON d247con_marcador.id = t247con_coordenada.id_marcador "
			+ "WHERE ( T247CCN_NODO_ARBOL.S_ALIAS_REPORTE = :aliasReporte"
			+ " or T247CCN_NODO_ARBOL.S_NOMBRE = :nombre )"
			+ " and   ( ( sdo_geom.sdo_distance(mdsys.sdo_geometry(2001,8307,mdsys.sdo_point_type("
			+ " :latitud, :Longitud, NULL),NULL,NULL),"
			+ "t247con_coordenada.clb_coordenada_punto,0.0001,'unit=KM') ) ) <  :radio", nativeQuery = true)
	List<Coordenada> validacionReporteVigiaPorRadio(@Param("latitud") final double latitud,
			@Param("Longitud") final double longitud, @Param("radio") final double radio,
			@Param("aliasReporte") final String aliasReporte, @Param("nombre") final String nombre);

}
