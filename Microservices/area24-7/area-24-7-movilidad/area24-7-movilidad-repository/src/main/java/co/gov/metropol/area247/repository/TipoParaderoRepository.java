package co.gov.metropol.area247.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.TipoParadero;


public interface TipoParaderoRepository extends CrudRepository<TipoParadero, Long>{
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_PARADERO 
	 * donde corresponda el id_item y su fuente de datos
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoParadero
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From TipoParadero e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	TipoParadero findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,@Param("fuenteDatos") final int fuenteDatos);

	/**
	 * Busca un tipo de paradero que coincida con el nombre y la fuente de
	 * datos.
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2,
	 *            GTPC), (3, METRO)
	 * @return un objeto de tipo {@link TipoParadero}
	 */
	TipoParadero findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);
	
	/**
	 * Busca un tipo de paradero que coincida con el identificador unico
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * 
	 * @return un objeto de tipo {@link TipoParadero}
	 */
	@Cacheable("tipoParaderos")
	TipoParadero findById(Long id);
	
}
