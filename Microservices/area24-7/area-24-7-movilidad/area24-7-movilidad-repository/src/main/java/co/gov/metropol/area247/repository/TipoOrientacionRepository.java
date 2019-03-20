package co.gov.metropol.area247.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.TipoOrientacion;


public interface TipoOrientacionRepository extends CrudRepository<TipoOrientacion, Long> {

	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_ORIENTACION 
	 * donde corresponda el id_item y su fuente de datos
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoOrientacion
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From TipoOrientacion e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	TipoOrientacion findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,@Param("fuenteDatos") final int fuenteDatos);
	
	/**
	 * Busca un tipo de orientacion que coincida con el nombre y la fuente de
	 * datos.
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda
	 * @return un objeto de tipo {@link TipoOrientacion}
	 */
	TipoOrientacion findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);
	
	/**
	 * Busca un tipo de orientacion que coincida con el identificador unico
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * @return un objeto de tipo {@link TipoOrientacion}
	 */
	@Cacheable("tipoOrientaciones")
	TipoOrientacion findById(Long id);
}
