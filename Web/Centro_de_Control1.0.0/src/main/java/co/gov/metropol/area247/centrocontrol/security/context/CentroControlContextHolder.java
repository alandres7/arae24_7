package co.gov.metropol.area247.centrocontrol.security.context;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import co.gov.metropol.area247.centrocontrol.model.Usuario;
//import co.businessteam.core.domain.enums.TipoRol;
//import co.businessteam.util.Util;
import co.gov.metropol.area247.centrocontrol.security.CentroControlAuthenticationToken;
import co.gov.metropol.area247.centrocontrol.security.utils.Util;

public class CentroControlContextHolder {
	
	/**
	 * Permite validar si un usuario esta autenticado
	 * 
	 * @return
	 */
	public static boolean isAuthenticate() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication != null) && authentication instanceof CentroControlAuthenticationToken) {
			return authentication.isAuthenticated();
		}
		return false;
	}

	/**
	 * Permite obtener el objecto de autenticacion
	 * 
	 * @return
	 */
	private static CentroControlAuthenticationToken getAuthentication() {
		if (isAuthenticate()) {
			return (CentroControlAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		}
		return null;
	}

		
	/**
	 * Permite obtener el usuario de sesion
	 * 
	 * @return
	 */
	public static Usuario getUsuarioSesion() {
		if (isAuthenticate()) {
			return (Usuario) getAuthentication().getPrincipal();
		}
		return null;
	}

	/**
	 * Permite obtener el username del usuario en sesion
	 * 
	 * @return
	 */
	public static String getUsernameUsuarioSesion() {
		if (isAuthenticate()) {
			return getUsuarioSesion().getUsername();
		}
		return null;
	}
	
	
	/**
	 * Permite obtener el id del usuario en sesion
	 * 
	 * @return
	 */
	public static Long getIdUsuarioSesion() {
		if (isAuthenticate()) {
			return getUsuarioSesion().getId();
		}
		return null;
	}
	
	

	
}
