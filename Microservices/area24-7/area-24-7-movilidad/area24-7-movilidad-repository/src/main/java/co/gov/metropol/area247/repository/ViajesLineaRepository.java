package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.ViajesLinea;

@Repository
public interface ViajesLineaRepository extends CrudRepository<ViajesLinea, Long> {

	/**
	 * Obtiene la informacion de los viajes de una linea
	 * <P>Creado 23/08/2018 10:23 a.m 
	 * @param idLinea - filtro de busqueda
	 * @return una lista de objetos {@link ViajesLinea}
	 */
	List<ViajesLinea> findByIdLinea(Long idLinea);
	
}
