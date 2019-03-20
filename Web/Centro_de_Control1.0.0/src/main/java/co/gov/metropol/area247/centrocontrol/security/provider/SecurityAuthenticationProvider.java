package co.gov.metropol.area247.centrocontrol.security.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.model.Usuario;
import co.gov.metropol.area247.centrocontrol.security.CentroControlAuthenticationToken;
import co.gov.metropol.area247.centrocontrol.security.repository.SecurityRepository;

@Scope("prototype")
@Component("authenticationProvider")
public class SecurityAuthenticationProvider implements AuthenticationProvider{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAuthenticationProvider.class);
	
	@Autowired
	private SecurityRepository securityRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		try {
			Usuario usuario = securityRepository.obtenerUsuarioPorUsername(username,password);
			//if ((usuario != null) && usuario.getContrasena().equals(password)) {
			if (usuario != null){
				List<Rol> roles = securityRepository.obtenerRoles();
				
				GrantedAuthority grantedAuthority = null;
				
				Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				grantedAuthority = new SimpleGrantedAuthority(usuario.getRol().getNombre());
				authorities.add(grantedAuthority);
				CentroControlAuthenticationToken newAuthentication = 
			        new CentroControlAuthenticationToken(usuario,usuario.getContrasena(), authorities);
				newAuthentication.setName(usuario.getNombre());
				
				return newAuthentication;
			}
		} catch (Exception e) {
			LOGGER.error("Ha ocurrido un error al tratar de autenticar al usuario {}", username, e);
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (CentroControlAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
