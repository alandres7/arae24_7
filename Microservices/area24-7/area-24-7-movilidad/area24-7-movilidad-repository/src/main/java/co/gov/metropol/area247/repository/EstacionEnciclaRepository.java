package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.EstacionEncicla;

@Repository
public interface EstacionEnciclaRepository extends CrudRepository<EstacionEncicla, Long>{
	
	static final String FORMULA_LOCALIZACION = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";
	
	/**
	 * Buscar si la estacion exite en base de datos
	 * @param idEstacion - identificador de la estacion proporcionada por encicla
	 * @return {@link EstacionEncicla}
	 * */
	EstacionEncicla findByIdEstacion(Long idEstacion);
	
	/**
	 * Obtener las estaciones dada una ubicacion
	 * @param latitud - latidud donde se encuenta la persona parada
	 * @param longitud - longitud donde se encuenta la persona parada
	 * @param radio - radio de busqueda
	 * @return Lista {@link EstacionEncicla}
	 * */
	@Query(name = "findByLocalizacion", value = "Select e From EstacionEncicla e Where "+FORMULA_LOCALIZACION+"  <= (:distance) ORDER BY "+FORMULA_LOCALIZACION+" ASC" )
	List<EstacionEncicla> findByLocalizacion(@Param("latitude") final double latitud, @Param("longitude") final double longitud, @Param("distance") final float radio);
	
}
