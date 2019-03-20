package co.gov.metropol.area247.repository;

import org.springframework.data.repository.CrudRepository;

import co.gov.metropol.area247.repository.domain.DareviaZona;

public interface DareviaZonaRepository extends CrudRepository<DareviaZona, Long>{

	/**
	 * Buscar si la zona exite en base de datos
	 * @param idZona - identificador de la zona proporcionada por encicla
	 * @return DareviaZona
	 * */
	DareviaZona findByIdZona(Long idZona);
	
}
