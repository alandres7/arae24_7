package co.gov.metropol.area247.centrocontrol.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import co.gov.metropol.area247.centrocontrol.model.Usuario;
//import co.businessteam.core.domain.enums.TipoRol;
//import co.businessteam.util.Util;
import co.gov.metropol.area247.centrocontrol.security.BusinessTeamAuthenticationToken;

public class BusinessTeamContextHolder {
	
	/**
	 * Permite validar si un usuario esta autenticado
	 * 
	 * @return
	 */
	public static boolean isAuthenticate() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication != null) && authentication instanceof BusinessTeamAuthenticationToken) {
			return authentication.isAuthenticated();
		}
		return false;
	}

	/**
	 * Permite obtener el objecto de autenticacion
	 * 
	 * @return
	 */
	private static BusinessTeamAuthenticationToken getAuthentication() {
		if (isAuthenticate()) {
			return (BusinessTeamAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
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
	
	
	
	
	/*public static boolean isRole(Collection<? extends GrantedAuthority> authorities, TipoRol role) {
		for (GrantedAuthority grantedAuthority : authorities) {
			if (role.toString().equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}

		return false;
	}*/

	/*public static boolean isSuperAdministrador(){
		BusinessTeamAuthenticationToken autentication = getAuthentication();
		if ( !Util.isNull(autentication) ){
			return isRole(autentication.getAuthorities(), TipoRol.administrador);
		}
		return false;
	}*/
	
	
}
