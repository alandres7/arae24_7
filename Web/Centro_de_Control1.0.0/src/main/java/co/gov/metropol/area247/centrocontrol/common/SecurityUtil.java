package co.gov.metropol.area247.centrocontrol.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import co.gov.metropol.area247.centrocontrol.model.Usuario;

public class SecurityUtil {

	public static String obtenerUsuarioLogueado() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = (Usuario) auth.getPrincipal();
			return usuario.getUsername();					
		} catch (Exception e) {
			return "area247";
		}
	}
}
