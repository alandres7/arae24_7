package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.util.constantes.Constantes;

@Repository
public interface RutaRepository extends CrudRepository<Ruta, Long> {

	/**
	 * Retorna un registro de la tabla T247VIA_RUTA donde corresponda el id_item
	 * y su fuente de datos
	 * 
	 * @param idItem
	 *            - idItem
	 * @param fuenteDatos
	 *            - fuenteDatos
	 * @return Ruta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From Ruta e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	Ruta findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem, @Param("fuenteDatos") final int fuenteDatos);

	/**
	 * retona la ruta mas cercana dada una ubicacin
	 * 
	 * @param latitud
	 *            - latitud
	 * @param longitud
	 *            - longitud
	 * @param radio
	 *            - radio
	 * @return Ruta
	 */
	@Query(name = "rutaCercana", value = "\r\n" + "select e from Ruta e\r\n"
			+ "where  SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 e.coordenadas, \r\n"
			+ "                                 0.0005 ,'unit=KM') < (:radius)")
	List<Ruta> rutaCercana(@Param("latitude") final double latitud, @Param("longitude") final double longitud,
			@Param("radius") final double radio);

	/**
	 * Buscar si la ruta exite en base de datos
	 * 
	 * @param idItem
	 *            - identificador de la estacion proporcionada por metro
	 * @return Ruta - Ruta
	 */
	Ruta findByIdItem(Long idItem);

	/**
	 * Busca la ruta que coincida con los argumentos definidos
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda
	 * 
	 * @return {@link Ruta}
	 */
	Ruta findByCodigoAndFuenteDatos(String codigo, Integer fuenteDatos);

	/**
	 * retorna las rutas que coinsidan con el parametro de busqueda en su codigo
	 * o descripcon
	 * 
	 * @param paramtero
	 *            codigo o descripcion de la ruta
	 * @return lista de rutas
	 */
	@Query(name = "findByCodigoOrDescripcion", value = "Select e " + "From Ruta e " + "Where "
			+ "UPPER(e.codigo) like %:parametro% OR " + "UPPER(translate(e.descripcion,'" + Constantes.ACENTOS
			+ "','" + Constantes.SIN_ACENTOS + "')) " + "like translate((%:parametro%),'" + Constantes.ACENTOS + "','"
			+ Constantes.SIN_ACENTOS + "')")
	List<Ruta> findByCodigoOrDescripcion(@Param("parametro") final String paramtero);

	/**
	 * Busca la ruta que coincida el codigo de ruta fijado como parametro.
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * 
	 * @return la ruta que coincida en el codigo de la ruta pasado como
	 *         parametro.
	 */
	Ruta findByCodigo(String codigo);
	
	/**
	 * retona la ruta mas cercana dada una ubicacin
	 * 
	 * @param latitud
	 *            - latitud
	 * @param longitud
	 *            - longitud
	 * @param radio
	 * 			  - area en la cual capturara las rutas cercanas
	 * @param modosTransporte 
	 *            - identificador de los modos de transporte a filtrar
	 *            
	 * @return rutas que pasen por el area del radio y que cumplan con los filtros
	 */
	@Query(name = "rutaCercana", value = "\r\n" + "select e from Ruta e\r\n"
			+ "where  SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n"
			+ "                                 e.coordenadas, \r\n"
			+ "                                 0.0005 ,'unit=KM') < (:radius) and e.idModoRuta in :modosTransporte")
	List<Ruta> rutaCercana(@Param("latitude") final double latitud, @Param("longitude") final double longitud,
			@Param("radius") final double radio, @Param("modosTransporte") final List<Long> modosTransporte);

	/**
	 * Obtiene todas las rutas activas
	 * Creado 2/08/2018 04:20:10 p.m
	 * @return {@link Ruta}
	 */
	@Query(name = "getAllActivas", value = "SELECT e FROM Ruta e WHERE e.rutaActiva = 'S'")
	List<Ruta> getAllActivas();
	
	/**
	 * Obtiene las rutas que pertenescan a la fuente de datos indicados como
	 * argumentos de entrada.
	 * <P>
	 * Creado 3/10/2018 03:30:10 p.m
	 * @param fuenteDatos - filtro de busqueda
	 * @return una lista de objetos {@link Ruta}
	 */
	List<Ruta> findByFuenteDatos(List<Integer> fuenteDatos);
	
	/**
	 * Obtiene las rutas que coincidan con los codigos y tipos de fuentes
	 * especificados en los parametros de entrada.
	 * <P>
	 * Creado 16/11/2018 3:03 p.m
	 * @param codigos - filtro de busqueda
	 * @param fuenteDatos - filtro de busqueda
	 * @return una lista de objetos {@link Ruta}
	 */
	@Query(name = "findListByCodigoAndFuenteDatos", value = "SELECT e FROM Ruta e WHERE e.codigo IN :codigos AND e.fuenteDatos = :fuenteDatos ")
	List<Ruta> findListByCodigoAndFuenteDatos(@Param("codigos") final List<String> codigos, @Param("fuenteDatos") final int fuenteDatos);
}
