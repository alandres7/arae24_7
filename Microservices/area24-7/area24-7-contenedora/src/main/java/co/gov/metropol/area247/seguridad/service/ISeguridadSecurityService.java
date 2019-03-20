package co.gov.metropol.area247.seguridad.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface ISeguridadSecurityService {

	String usuarioUsernameGetLoggedIn();
	void autologin(String username, String contrasena) throws BadCredentialsException;
	
	UsernamePasswordAuthenticationToken newAuthenticate(String username, String password)throws BadCredentialsException;
	
}
