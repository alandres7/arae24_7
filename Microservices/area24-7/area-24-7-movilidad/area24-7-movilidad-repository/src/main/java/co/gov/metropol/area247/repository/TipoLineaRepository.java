package co.gov.metropol.area247.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.TipoLinea;

@Repository
public interface TipoLineaRepository extends CrudRepository<TipoLinea, Long> {

	/**
	 * Buscar el tipo de linea que coincida con el identificador alterno
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * @return - {@link TipoLinea}
	 */
	@Cacheable("tipoLineas")
	TipoLinea findById(Long id);

	/**
	 * Buscar el tipo de linea que coincida con el nombre
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @return - {@link TipoLinea}
	 */
	TipoLinea findByNombre(String nombre);

}
