package co.gov.metropol.area247.core.repository;

import co.gov.metropol.area247.core.domain.Icono;
import co.gov.metropol.area247.core.repository.data.CrudRepository;

public interface IconoRepository extends CrudRepository<Icono>{

	Long getIconoClimaActual(long idMarcador);
	
}
