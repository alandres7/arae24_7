package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.PermisoRol;

@Repository
public interface ICentroControlPermisoRolRepository extends CrudRepository<PermisoRol, Long> {
	
	List<PermisoRol> findByIdRol(Long idRol);
	
	List<PermisoRol> findByIdObjeto(Long idObjeto);
}
