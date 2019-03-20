package co.gov.metropol.area247.seguridad.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.service.ISeguridadSecurityService;

@Service
public class ISeguridadSecurityServiceImpl implements ISeguridadSecurityService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	private static Logger LOGGER = LoggerFactory.getLogger(ISeguridadSecurityServiceImpl.class);

	@Override
	public String usuarioUsernameGetLoggedIn() {

		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}

	@Override
	public void autologin(String username, String contrasena) throws BadCredentialsException {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, contrasena, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			LOGGER.debug(String.format("Auto login para usuario %s exitoso", username));
		}
	}

	public UsernamePasswordAuthenticationToken newAuthenticate(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		return usernamePasswordAuthenticationToken;
	}

}
