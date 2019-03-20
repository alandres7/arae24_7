package co.gov.metropol.area247.seguridad.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.Genero;

@Repository
public interface ISeguridadGeneroRepository extends CrudRepository<Genero, Long> {

	Genero findByNombre(String nombre);
	
}
