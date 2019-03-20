package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.FrecuenciaRuta;

@Repository
public interface FrecuenciaRutaMetroRepository extends CrudRepository<FrecuenciaRuta, Long> {

	static final String FORMULA_LOCALIZACION = "6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2))))))";

	/**
	 * 
	 * @param idItem
	 *            - idItem
	 * @return FrecuenciaRuta
	 */
	FrecuenciaRuta findByIdItem(Long idItem);

	/**
	 * Busca todas las frecuencias de las rutas que coincidan con el id de la
	 * ruta que se defina como argumentos.
	 * 
	 * @param idRuta
	 *            - filtro de busqueda
	 * 
	 * @return una lista de objetos {@link FrecuenciaRuta}
	 */
	List<FrecuenciaRuta> findByIdRuta(Long idRuta);
}
