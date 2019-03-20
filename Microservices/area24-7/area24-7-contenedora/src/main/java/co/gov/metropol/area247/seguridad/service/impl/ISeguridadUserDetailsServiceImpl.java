package co.gov.metropol.area247.seguridad.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadUsuarioRepository;
import co.gov.metropol.area247.seguridad.model.Usuario;

@Service
@Component
public class ISeguridadUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	ISeguridadUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{

		Usuario usuario = usuarioRepository.findByUsername(username);
	
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority((usuario.getRol().getNombre())));
		
		return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getContrasena(), grantedAuthorities);
		
	}
	
	
}
