package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.util.constantes.Constantes;

@Repository
public interface LineaMetroRepository extends CrudRepository<LineaMetro, Long> {
	
	/**
	 * Buscar si la linea exite en base de datos
	 * 
	 * @param idLinea
	 *            - identificador de la estacion proporcionada por metro
	 * @return LineaMetro
	 */
	LineaMetro findByIdLinea(Long idLinea);

	/**
	 * Obtiene todas las lineas por codigo o nombre
	 * 
	 * @param parametro
	 *            codigo o nombre de la linea
	 *            
	 * @return lista de objetos tipo {@link LineaMetro}
	 */
	@Query(name = "findByCodigoOrDescripcion", value = "Select l " + "From LineaMetro l " + "Where "
			+ "UPPER(l.codigo) like %:parametro% or " + "UPPER(translate(l.descripcion,'" + Constantes.ACENTOS + "','"
			+ Constantes.SIN_ACENTOS + "')) like translate((%:parametro%),'" + Constantes.ACENTOS + "','"
			+ Constantes.SIN_ACENTOS + "')")
	List<LineaMetro> findByCodigoOrDescripcion(@Param("parametro") final String parametro);

	/**
	 * Obtiene la informacion de la linea que coincida con el parametro de entrada
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * 
	 * @return {@link LineaMetro}
	 */
	LineaMetro findByCodigo(String codigo);

	/**
	 * retona la linea mas cercana dada una ubicacion
	 * 
	 * @param latitud
	 *            - latitud
	 * @param longitud
	 *            - longitud
	 * @param radio
	 *            - radio
	 * @return lista de LineaMetro
	 */
	@Query(name = "lineaCercana", value = "select e from LineaMetro e \r\n"
			+ "where  SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 e.coordenadas, \r\n"
			+ "                                 0.0005 ,'unit=KM') < (:radius)")
	List<LineaMetro> lineaCercana(@Param("latitude") final double latitud, @Param("longitude") final double longitud,
			@Param("radius") final double radio);
	
	/**
	 * retona la linea mas cercana dada una ubicacion
	 * 
	 * @param latitud
	 *            - latitud
	 * @param longitud
	 *            - longitud
	 * @param radio
	 *            - radio
	 * @param modosTransporte 
	 * 			  - modos de transporte(cable, tranvia, metro, etc)
	 * @return lista de LineaMetro
	 */
	@Query(name = "lineaCercana", value = "select e from LineaMetro e \r\n"
			+ "where SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 e.coordenadas, \r\n"
			+ "                                 0.0005 ,'unit=KM') < (:radius) and e.idModoLinea in :modosTransporte")
	List<LineaMetro> lineaCercana(@Param("latitude") final double latitud, @Param("longitude") final double longitud,
			@Param("radius") final double radio, @Param("modosTransporte") final List<Long> modosTransporte);

	/**
	 * Obtiene todas las lineas del metro activas
	 * Creado 16/07/2018 04:03:10 p.m
	 * @return {@link LineaMetro}
	 */
	@Query(name = "getAllActivas", value = "SELECT e FROM LineaMetro e WHERE e.activo = 'S'")
	List<LineaMetro> getAllActivas();
}