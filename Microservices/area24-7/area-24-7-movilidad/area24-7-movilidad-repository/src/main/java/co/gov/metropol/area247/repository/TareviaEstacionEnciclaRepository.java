package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.TareviaEstacionEncicla;

public interface TareviaEstacionEnciclaRepository extends CrudRepository<TareviaEstacionEncicla, Long>{
	
	static final String FORMULA_LOCALIZACION2 = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2) * sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2)+cos((3.14 * e.latitudEstacionEncila)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)* sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitudEstacionEncila - (:latitude)))/180)/2) *sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2) +cos((3.14 * e.latitudEstacionEncila)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)* sin(((3.14 * (e.longitudEstacionEncila - (:longitude)))/180)/2))))))";
	static final String FORMULA_LOCALIZACION_NATIVO = "6371*(2*atan2 (sqrt(sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2) * sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2)+cos((3.14 * PR.N_LATITUD)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (PR.N_LATITUD - (:latitude)))/180)/2) *sin(((3.14*(PR.N_LATITUD - (:latitude)))/180)/2) +cos((3.14 * PR.N_LATITUD)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(PR.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14 * (PR.N_LONGITUD - (:longitude)))/180)/2))))))";
	/**
	 * Buscar si la estación exite en base de datos
	 * @param idEstacionEncicla -identificador de la estación proporcionada por encicla
	 * @return TareviaEstacionEncicla
	 */
	TareviaEstacionEncicla findByIdEstacionEncicla(Long idEstacionEncicla);
	
	/**
	 * Obtener las estaciones dada una ubicacion
	 * @param latitud - latidud donde se encuenta la persona parada
	 * @param longitud - longitud donde se encuenta la persona parada
	 * @param radio - radio de busqueda
	 * @return Lista {@link TareviaEstacionEncicla}
	 * */
	@Query(name = "findByLocalizacion", value = "SELECT * FROM MOVILIDAD.T247VIA_ESTACION_ENCICLA PR WHERE "
			+ FORMULA_LOCALIZACION_NATIVO + "  <= (:distance) ORDER BY " + FORMULA_LOCALIZACION_NATIVO
			+ " ASC", nativeQuery = true)
	List<TareviaEstacionEncicla> findByLocalizacion(@Param("latitude") final double latitud, @Param("longitude") final double longitud, @Param("distance") final double radio);
}
