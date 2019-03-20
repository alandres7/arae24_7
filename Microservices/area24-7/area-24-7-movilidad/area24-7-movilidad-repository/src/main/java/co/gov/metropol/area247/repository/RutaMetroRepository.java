package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.Ruta;

@Repository
public interface RutaMetroRepository extends CrudRepository<Ruta, Long> {

	/**
	 * Buscar si la ruta exite en base de datos
	 * 
	 * @param idItem
	 *            - identificador de la estacion proporcionada por metro
	 * @return Ruta - Ruta
	 */
	Ruta findByIdItem(Long idItem);

	/**
	 * Busca la ruta del metro que coincida con los argumentos definidos
	 * 
	 * @param idItem
	 *            - identificador que viene desde el servicio del metro
	 * @param fuenteDatos
	 *            - filtro de la fuente de datos (1, ENCICLA), (2, GTPC), (3,
	 *            METRO)
	 * @return un objeto de tipo {@link Ruta}
	 */
	Ruta findByIdItemAndFuenteDatos(Long idItem, Integer fuenteDatos);

}