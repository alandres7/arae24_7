package co.gov.metropol.area247.seguridad.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.FuenteRegistro;

@Repository
public interface ISeguridadFuenteRegistroRepository extends CrudRepository<FuenteRegistro, Long> {

	FuenteRegistro findByNombre(String nombre);
	
}
