package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.VehiculosRunt;

@Repository
public interface VehiculoRuntRepository extends CrudRepository<VehiculosRunt, Long>{
	
	/**
	 * Buscar un vehiculo en la base de datos del RUNT utilizando la placa
	 * @param placa - placa del vehiculo
	 * @return {@link VehiculosRunt}
	 */
	VehiculosRunt findByPlaca (String placa);

}
