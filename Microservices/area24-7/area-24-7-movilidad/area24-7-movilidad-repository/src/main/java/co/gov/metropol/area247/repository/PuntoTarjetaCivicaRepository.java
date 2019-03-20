package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.PuntoTarjetaCivica;

@Repository
public interface PuntoTarjetaCivicaRepository extends CrudRepository<PuntoTarjetaCivica, Long>{
	static final String FORMULA_LOCALIZACION = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";
	static final String FORMULA_LOCALIZACION_NATIVO = "6371*(2*atan2 (sqrt(sin(((3.14*(PTC.N_LATITUD - (:latitude)))/180)/2) * sin(((3.14*(PTC.N_LATITUD - (:latitude)))/180)/2)+cos((3.14 * PTC.N_LATITUD)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(PTC.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14*(PTC.N_LONGITUD - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (PTC.N_LATITUD - (:latitude)))/180)/2) *sin(((3.14*(PTC.N_LATITUD - (:latitude)))/180)/2) +cos((3.14 * PTC.N_LATITUD)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(PTC.N_LONGITUD - (:longitude)))/180)/2)* sin(((3.14 * (PTC.N_LONGITUD - (:longitude)))/180)/2))))))";
	
	/**
	 * Obtener los puntos de Tarjeta Civica dada una ubicacion
	 * @param latitud - latidud donde se encuenta la persona parada
	 * @param longitud - longitud donde se encuenta la persona parada
	 * @param radio - radio de busqueda
	 * @return Lista {@link PuntoTarjetaCivica}
	 * */
	//@Query(name = "findByLocalizacion", value = "Select e From PuntoTarjetaCivica e Where "+FORMULA_LOCALIZACION+"  <= (:radio) ORDER BY "+FORMULA_LOCALIZACION+" ASC" )
	@Query(name = "findByLocalizacion", value = "SELECT * FROM MOVILIDAD.T247VIA_PUNTO_TARJETA_CIVICA PTC WHERE "
			+ FORMULA_LOCALIZACION_NATIVO + "  <= (:radio) ORDER BY " + FORMULA_LOCALIZACION_NATIVO
			+ " ASC", nativeQuery = true)
	List<PuntoTarjetaCivica> findByLocalizacion(@Param("latitude") final double latitud, @Param("longitude") final double longitud, @Param("radio") final double radio);
	
	/**
	 * Obtiene un registo de la tabla tarjeta civica por su idItem y su tipo
	 * Creado el 27/12/2017 a las  4:44:01 p. m. <br>
	 * @param idItem - filtro de busqueda
	 * @param tipoPunto - filtro de busqueda
	 * @return PuntoTarjetaCivica
	 */
	@Query(name = "findByIdItem", value = "\r\n" + 
			"select e from PuntoTarjetaCivica e\r\n" + 
			"where  e.idItem= (:iditem) and e.tipoPunto=:tipoPunto")
	PuntoTarjetaCivica findByIdItemAndTipoPunto(@Param("iditem") final Long idItem, @Param("tipoPunto") final String tipoPunto);
}
