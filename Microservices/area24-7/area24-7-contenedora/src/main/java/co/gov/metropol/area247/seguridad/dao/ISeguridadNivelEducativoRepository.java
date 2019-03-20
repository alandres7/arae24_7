package co.gov.metropol.area247.seguridad.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.NivelEducativo;

@Repository
public interface ISeguridadNivelEducativoRepository extends CrudRepository<NivelEducativo, Long> {

	NivelEducativo findByNombre(String nombre);
	
}
