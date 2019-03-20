package co.gov.metropol.area247.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.ModoEstacion;

@Repository
public interface ModoEstacionRepository extends CrudRepository<ModoEstacion, Long> {
	
	/**
	 * Obtener el modo de estacion
	 * 
	 * @param id - identificador
	 * @return ModoEstacion
	 */
	@Cacheable("modoEstaciones")
	ModoEstacion findById(Long id);
	
	/**
	 * Obtener el modo de estacion a partir del nombre
	 * 
	 * @param nombre - nombre de la estacion
	 * @return ModoEstacion
	 */
	ModoEstacion findByNombre(String nombre);

}
