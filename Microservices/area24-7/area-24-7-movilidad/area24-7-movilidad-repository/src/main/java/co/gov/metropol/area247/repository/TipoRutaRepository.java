package co.gov.metropol.area247.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.TipoRuta;

public interface TipoRutaRepository extends CrudRepository<TipoRuta, Long> {

	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_RUTA donde corresponda el
	 * id_item y su fuente de datos
	 * 
	 * @param idItem
	 *            - idItem
	 * @param fuenteDatos
	 *            - fuenteDatos
	 * @return TipoRuta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From TipoRuta e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	TipoRuta findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,
			@Param("fuenteDatos") final int fuenteDatos);

	/**
	 * Busca un tipo de ruta que coincida con el nombre y la fuente de datos.
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2, GTPC),
	 *            (3, METRO)
	 * @return un objeto de tipo {@link TipoRuta}
	 */
	TipoRuta findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);
}
