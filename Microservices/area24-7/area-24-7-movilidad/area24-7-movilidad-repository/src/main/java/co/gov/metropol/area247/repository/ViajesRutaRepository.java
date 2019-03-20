package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.ViajesRuta;

@Repository
public interface ViajesRutaRepository extends CrudRepository<ViajesRuta, Long> {

	/**
	 * Obtiene la informacion de los viajes de una ruta
	 * <P>Creado 23/08/2018 10:23 a.m 
	 * @param idRuta - filtro de busqueda
	 * @return una lista de objetos {@link ViajesRuta}
	 */
	List<ViajesRuta> findByIdRuta(Long idRuta);
	
}
