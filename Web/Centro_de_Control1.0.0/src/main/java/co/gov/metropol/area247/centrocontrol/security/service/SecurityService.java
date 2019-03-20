package co.gov.metropol.area247.centrocontrol.security.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.security.service.ex.SecurityServiceException;

public interface SecurityService {
	
	/**
	 * Permite autenticar un usuario en el sistema.
	 * 
	 * @param usuario
	 * @param contrasena
	 * @return
	 * @throws SecurityServiceException
	 */
	public boolean authenticate(String usuario, String contrasena) throws SecurityServiceException;

	/**
	 * Permite consultar el listado de roles
	 * 
	 * @return la lista de roles
	 * @throws SecurityServiceException
	 */
	List<Rol> consultarRoles() throws SecurityServiceException;

}
