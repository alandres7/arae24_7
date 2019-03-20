package co.gov.metropol.area247.centrocontrol.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;


public interface ISeguridadPermisoRolService {
	
	List<PermisoRol> permisoRolListarTodos();
	
	public List<PermisoRol> findByIdRol(Long idRol);
	
	public PermisoRol permisoRolObtenerPermisoPorRolPorObjeto(Long idRol, Long idObjeto);
	
	public boolean safeUpdatePermisoRol(PermisoRol permisoRol);
	
}
