package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;

import co.gov.metropol.area247.repository.domain.OperadorEmpresa;

public interface OperadorEmpresaRepository extends CrudRepository<OperadorEmpresa, Long>{
	
	/**
	 * Retorna un registro de la tabla T247VIA_OPERADOR_EMPRESA
	 * que corresponda con su idItem
	 * @param idItem - idItem
	 * @return OperadorEmpresa 
	 */
	OperadorEmpresa findByIdItem(Long idItem);
}
