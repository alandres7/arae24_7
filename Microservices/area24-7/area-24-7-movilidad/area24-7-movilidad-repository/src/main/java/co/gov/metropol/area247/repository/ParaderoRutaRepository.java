package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.ParaderoRuta;

public interface ParaderoRutaRepository extends CrudRepository<ParaderoRuta, Long> {

	static final String FORMULA_LOCALIZACION = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";
	static final String FORMULA_LOCALIZACION_NATIVO = "6371*(2*atan2 (sqrt(sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2) * sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2)+cos((3.14 * PR.N_LATITUD)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (PR.N_LATITUD - (:latitude)))/180)/2) *sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2) +cos((3.14 * PR.N_LATITUD)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14 * (PR.N_LONGITUD - (:longitude)))/180)/2))))))";

	/**
	 * Retorna un registro de la tabla T247VIA_PARADERO_RUTA donde corresponda el
	 * id_item y su fuente de datos
	 * 
	 * @param idItem
	 *            - idItem
	 * @param fuenteDatos
	 *            - fuenteDatos
	 * @return ParaderoRuta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From ParaderoRuta e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	ParaderoRuta findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,
			@Param("fuenteDatos") final int fuenteDatos);

	/**
	 * Obtener los paraderos dada una ubicacion
	 * 
	 * @param latitud
	 *            - latidud donde se encuenta la persona parada
	 * @param longitud
	 *            - longitud donde se encuenta la persona parada
	 * @param radio
	 *            - radio de busqueda
	 * @return Lista {@link ParaderoRuta}
	 */
	@Query(name = "findByLocalizacion", value = "Select e From ParaderoRuta e Where " + FORMULA_LOCALIZACION
			+ "  <= (:distance) ORDER BY " + FORMULA_LOCALIZACION + " ASC")
	List<ParaderoRuta> findByLocalizacion(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud, @Param("distance") final double radio);

	/**
	 * Obtener el paradero que coincida con el codigo y la fuente de datos
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda
	 * @return ParaderoRuta
	 */
	ParaderoRuta findByCodigoAndFuenteDatos(String codigo, Integer fuenteDatos);

	/**
	 * obtiene los paraderos pertenecientes a una ruta especifica
	 * @param idRuta id de la ruta
	 * @return lista de ParaderoRuta
	 */
	@Query(name = "findByIdRuta", value = "Select e From ParaderoRuta e Where e.idRuta = :idRuta")
	List<ParaderoRuta> findByIdRuta(@Param("idRuta") final Long idRuta);
	
	/**
	 * Obtener los paraderos cercanos a un punto dado y que sean de las rutas
	 * especificadas
	 * 
	 * @param latitud
	 *            - latidud donde se encuenta la persona parada
	 * @param longitud
	 *            - longitud donde se encuenta la persona parada
	 * @param radio
	 *            - radio de busqueda
	 * @param idsRutas
	 *            - filtro de busqueda de los paraderos
	 * 
	 * @return Lista {@link ParaderoRuta}
	 */
	@Query(name = "findByLocalizacion", value = "Select e From ParaderoRuta e Where " + FORMULA_LOCALIZACION
			+ "  <= (:distance) and  e.idRuta in :idsRutas ORDER BY " + FORMULA_LOCALIZACION + " ASC")
	List<ParaderoRuta> findByLocalizacion(@Param("latitude") final double latitud,
			@Param("longitude") final double longitud, @Param("distance") final double radio,
			@Param("idsRutas") final List<Long> idsRutas);
	
	/**
	 * definicion 
	 * 
	 * @param latitud - filtro 
	 * @param longitud -filtro
	 * @param radio - filtro
	 * @param modosTransporte - filtro 
	 * @return - los paraderos que cumplan con los filtros
	 */
	@Query(name = "findByLocalizacion", 
			value = "SELECT * FROM MOVILIDAD.T247VIA_PARADERO_RUTA PR INNER JOIN MOVILIDAD.T247VIA_RUTA R ON (R.ID = PR.ID_RUTA AND R.ID_MODO_RUTA IN :modosTransporte) WHERE "
			+ FORMULA_LOCALIZACION_NATIVO + "  <= (:radio) ORDER BY " + FORMULA_LOCALIZACION_NATIVO + " ASC",
			nativeQuery=true )
	List<ParaderoRuta> findByLocalizacion(@Param("modosTransporte") final List<Long> modosTransporte, @Param("latitude") final double latitud,
			@Param("longitude") final double longitud, @Param("radio") final double radio);
	
	/**
	 * Obtiene todos los paraderos con estado activo
	 * <P>Creado 6/08/2018 10:32 a.m
	 * @return una lista de objetos tipo {@link ParaderoRuta}
	 */
	@Query(name = "findAllActivas", value = "SELECT e FROM ParaderoRuta e WHERE e.activo = 'S'")
	List<ParaderoRuta> findAllActivas();
	
	/**
	 * Obtiene todos los paraderos que coincidan con las fuentes de datos
	 * definida como argumentos
	 * 
	 * @param fuenteDatos - filtro de busqueda
	 * @return objetos de tipo {@link ParaderoRuta}
	 */
	List<ParaderoRuta> findByFuenteDatos(List<Integer> fuenteDatos);
}
