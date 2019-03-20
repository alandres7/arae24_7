package co.gov.metropol.area247.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.seguridad.model.Rol;

public interface ISeguridadRolService {

	Rol rolObtenerPorNombre(String nombreRol);
	Rol rolObtenerPorId(Long idRol);
	List<Rol> rolObtenertodos();
	
}
