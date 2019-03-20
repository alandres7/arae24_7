package co.gov.metropol.area247.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.TipoSistemaRuta;

public interface TipoSistemaRutaRepository extends CrudRepository<TipoSistemaRuta, Long>{

	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_SISTEMA_RUTA 
	 * donde corresponda el id_item y su fuente de datos
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoRuta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From TipoSistemaRuta e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	TipoSistemaRuta findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,@Param("fuenteDatos") final int fuenteDatos);
	
	/**
	 * Busca un tipo de sistema de ruta que coincida con el nombre y la fuente de
	 * datos.
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2,
	 *            GTPC), (3, METRO)
	 * @return un objeto de tipo {@link TipoSistemaRuta}
	 */
	TipoSistemaRuta findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);
	
	/**
	 * Busca un tipo de sistema ruta que coincida con el campo idItem
	 * @param idItem - filtro de busqueda
	 * @return un objeto de tipo {@link TipoSistemaRuta}
	 */
	TipoSistemaRuta findByIdItem(Long idItem);
}
