package co.gov.metropol.area247.centrocontrol.security.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.model.Rol;
import co.gov.metropol.area247.centrocontrol.security.CentroControlAuthenticationToken;
import co.gov.metropol.area247.centrocontrol.security.repository.SecurityRepository;
import co.gov.metropol.area247.centrocontrol.security.service.SecurityService;
import co.gov.metropol.area247.centrocontrol.security.service.ex.SecurityServiceException;
import co.gov.metropol.area247.centrocontrol.security.utils.Util;

@Service
public class SecurityServiceImpl implements SecurityService{  //esta interfaz tiene un metodo autenticate
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityRepository securityRepository;

	@Override
	public boolean authenticate(String usuario, String contrasena) throws SecurityServiceException {
		try {
			CentroControlAuthenticationToken newAuthentication 
			    = (CentroControlAuthenticationToken) authenticationManager.authenticate(new CentroControlAuthenticationToken(usuario, contrasena));
              
			if (!Util.isNull(newAuthentication) && newAuthentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(newAuthentication);
				return true;
			}

			throw new SecurityServiceException("Error en el servicio de autenticacion");
		} catch (Exception e) {
			LOGGER.error("Inicio de sesion fallido, datos incorrectos para {}", usuario,e);
			return false;
		}
	}

	
	
	@Override
	public List<Rol> consultarRoles() throws SecurityServiceException {
		try {
			return securityRepository.obtenerRoles();
		} catch (Exception e) {
			LOGGER.error("Error al consultar la lista de roles", e);
			throw new SecurityServiceException("Error al consultar la lista de roles", e);
		}
	}

}
