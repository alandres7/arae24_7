package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;

import co.gov.metropol.area247.repository.domain.Operador;

public interface OperadorRepository extends CrudRepository<Operador, Long>{
	
	/**
	 * Retorna un registro de la tabla T247VIA_OPERADOR
	 * que corresponda con su idItem
	 * @param idItem - idItem
	 * @return Operador 
	 */
	Operador findByIdItem(Long idItem);
}
