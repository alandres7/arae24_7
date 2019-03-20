package co.gov.metropol.area247.seguridad.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.Rol;

@Repository
public interface ISeguridadRolRepository extends CrudRepository<Rol, Long> {
	
	Rol findByNombre(String nombre);
	
	Rol findById(Long id);
}
