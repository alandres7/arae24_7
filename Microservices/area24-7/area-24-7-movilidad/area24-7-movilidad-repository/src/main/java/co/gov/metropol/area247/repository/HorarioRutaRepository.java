package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.HorarioRuta;

@Repository
public interface HorarioRutaRepository extends CrudRepository<HorarioRuta, Long> {

static final String FORMULA_LOCALIZACION = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";
	
	/**
	 * Retorna un registro de la tabla T247VIA_HORARIO_RUTA 
	 * donde corresponda el id_item y su fuente de datos
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return HorarioRuta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From HorarioRuta e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	HorarioRuta findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,@Param("fuenteDatos") final int fuenteDatos);
	
	/**
	 * Retorna una lista de la tabla T247VIA_HORARIO_RUTA 
	 * donde corresponda el idRuta
	 * @param idRuta - idRuta
	 * @return HorarioRuta
	 */
	@Query(name = "findByIdRuta", value = "Select e From HorarioRuta e Where e.idRuta = (:idRuta)")
	List<HorarioRuta> findByIdRuta(@Param("idRuta") final Long idRuta);
}
