package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.EstacionMetro;
import co.gov.metropol.area247.repository.domain.TareviaEstacionEncicla;

@Repository
public interface EstacionLineaMetroRepository extends CrudRepository<EstacionMetro, Long> {

	static final String FORMULA_LOCALIZACION2 = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";
	static final String FORMULA_LOCALIZACION_NATIVO = "6371*(2*atan2 (sqrt(sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2) * sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2)+cos((3.14 * PR.N_LATITUD)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (PR.N_LATITUD - (:latitude)))/180)/2) *sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2) +cos((3.14 * PR.N_LATITUD)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14 * (PR.N_LONGITUD - (:longitude)))/180)/2))))))";
	/**
	 * obtener la estacion por su id
	 * 
	 * @param idEstacion
	 *            - filtro de busqueda
	 * @return obj EstacionMetro
	 */
	EstacionMetro findByIdEstacion(Long idEstacion);

	/**
	 * obtener las estaciones pertenecientes a una linea en particular
	 * 
	 * @param idLinea - identificador de la linea
	 * @return lista de EstacionMetro
	 */
	@Query(name = "findByIdLinea", value = "Select e From EstacionMetro e Where e.idLinea = :idLinea")
	List<EstacionMetro> findByIdLinea(@Param("idLinea") final Long idLinea);

	/**
	 * obtener la estacion por su codigo y por el id de la linea ya que pueden
	 * haber mas de una estacion pero de diferentes lineas
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * @param idLinea
	 * 			  - filtro de busqueda
	 * 
	 * @return obj EstacionMetro
	 */
	EstacionMetro findByCodigoAndIdLinea(String codigo, Long idLinea);
	
	/**
	 * Obtener las estaciones dada una ubicacion
	 * @param latitud - latidud donde se encuenta la persona parada
	 * @param longitud - longitud donde se encuenta la persona parada
	 * @param radio - radio de busqueda
	 * @return Lista {@link TareviaEstacionEncicla}
	 * */
	@Query(name = "findByLocalizacion", value = "Select e From EstacionMetro e Where "+FORMULA_LOCALIZACION2+"  <= (:distance) ORDER BY "+FORMULA_LOCALIZACION2+" ASC" )
	List<EstacionMetro> findByLocalizacion(@Param("latitude") final double latitud, @Param("longitude") final double longitud, @Param("distance") final double radio);
	
	/**
	 * Obtener las estaciones dada una ubicacion
	 * @param latitud - latidud donde se encuenta la persona parada
	 * @param longitud - longitud donde se encuenta la persona parada
	 * @param radio - radio de busqueda
	 * @param idsLineas - identificadores de las lineas para filtrar
	 * @return Lista {@link TareviaEstacionEncicla}
	 * */
	@Query(name = "findByLocalizacion", value = "Select e From EstacionMetro e Where "+FORMULA_LOCALIZACION2+"  <= (:distance) AND e.idLinea in :idsLineas ORDER BY "+FORMULA_LOCALIZACION2+" ASC" )
	List<EstacionMetro> findByLocalizacion(@Param("latitude") final double latitud, @Param("longitude") final double longitud, @Param("distance") final double radio, @Param("idsLineas") final List<Long> idsLineas);
	
	/**
	 * Obtener las estaciones cercanas a una ubicacion dada.
	 * 
	 * @param modosTransporte
	 *            - identificadores de las lineas para filtrar
	 * @param latitud
	 *            - latidud donde se encuenta la persona parada
	 * @param longitud
	 *            - longitud donde se encuenta la persona parada
	 * @param radio
	 *            - radio de busqueda
	 * 
	 * @return Lista {@link TareviaEstacionEncicla}
	 */
	@Query(name = "findByLocalizacion", value = "SELECT * FROM MOVILIDAD.T247VIA_ESTACION_METRO PR INNER JOIN MOVILIDAD.T247VIA_LINEA_METRO R ON (R.ID = PR.ID_LINEA AND R.ID_MODO_LINEA IN :modosTransporte) WHERE "
			+ FORMULA_LOCALIZACION_NATIVO + "  <= (:radio) ORDER BY " + FORMULA_LOCALIZACION_NATIVO + " ASC",
			nativeQuery=true)
	List<EstacionMetro> findByLocalizacion(@Param("modosTransporte") final List<Long> modosTransporte,
			@Param("latitude") final double latitud, @Param("longitude") final double longitud,
			@Param("radio") final double radio);

	/**
	 * Obtiene todas las estaciones del metro activas
	 * Creado 24/07/2018 10:42:00 a.m
	 * @return lista de objetos tipo {@link EstacionMetro}
	 */
	@Query(name = "getAllActivas", value = "SELECT e FROM EstacionMetro e WHERE e.activo = 'S'")
	List<EstacionMetro> getAllActivas();

}
