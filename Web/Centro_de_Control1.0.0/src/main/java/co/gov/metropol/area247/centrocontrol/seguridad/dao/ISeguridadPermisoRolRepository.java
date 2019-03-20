package co.gov.metropol.area247.centrocontrol.seguridad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.dto.SecurityDTO;
import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.security.service.ex.SecurityServiceException;



@Repository
public interface ISeguridadPermisoRolRepository extends CrudRepository<PermisoRol, Long> {

	List<PermisoRol> findByIdRol(Long idRol);
	
	/*@Query("select new co.gov.metropol.area247.centrocontrol.model.PermisoRol (pr) from PermisoRol where pr.idRol=:idRol and pr.idObjeto=:idObjeto")
	public PermisoRol permisoRolObtenerPermisoPorRolPorObjeto(@Param("idRol")long idRol, @Param("idObjeto")long idObjeto) throws SecurityServiceException;*/
	


	
}
