package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.FrecuenciaRuta;

public interface FrecuenciaRutaRepository extends CrudRepository<FrecuenciaRuta, Long> {

static final String FORMULA_LOCALIZACION = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";
	
	/**
	 * Retorna un registro de la tabla T247VIA_FRECUENCIA_RUTA 
	 * donde corresponda el id_item y su fuente de datos
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return FrecuenciaRuta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From FrecuenciaRuta e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	FrecuenciaRuta findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,@Param("fuenteDatos") final int fuenteDatos);
	
	/**
	 * Retorna un registro de la tabla T247VIA_FRECUENCIA_RUTA 
	 * donde corresponda el id_t247via_ruta
	 * @param idRuta - idRUta
	 * @return FrecuenciaRuta
	 */
	@Query(name = "findFrecuenciaRutaByIdRuta", value = "Select e From FrecuenciaRuta e Where e.idRuta = (:idRuta)")
	List<FrecuenciaRuta> findFrecuenciaRutaByIdRuta(@Param("idRuta") final Long idRuta);
}
