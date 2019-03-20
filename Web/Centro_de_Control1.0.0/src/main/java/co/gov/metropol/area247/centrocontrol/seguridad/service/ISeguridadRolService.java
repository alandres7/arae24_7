package co.gov.metropol.area247.centrocontrol.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Rol;


public interface ISeguridadRolService {
	
	List<Rol> rolListarTodos();
	
	public Rol getRolById(Long id);
	
	public boolean safeUpdateRol(Rol rol);
	
}
