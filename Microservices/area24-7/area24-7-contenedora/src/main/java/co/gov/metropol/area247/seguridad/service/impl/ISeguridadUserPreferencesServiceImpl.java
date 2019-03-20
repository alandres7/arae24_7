package co.gov.metropol.area247.seguridad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.seguridad.dao.ISeguridadUsuarioRepository;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadUserPreferencesService;

@Service
public class ISeguridadUserPreferencesServiceImpl implements ISeguridadUserPreferencesService{
	
	private Usuario usuario;
		
	@Autowired
	ISeguridadUsuarioRepository usuarioDao;
	
	@Override
	public String getUserPreferences(String username) {
		usuario = usuarioDao.findByUsername(username);
		String preferencias = null;
		if(usuario != null) {
			preferencias = usuario.getPreferencias();
		}
		return preferencias;
	}
	
	@Override
	public String updatePreferences(String username, String preferencias) {
		usuario = usuarioDao.findByUsername(username);
		if(usuario != null) {
			usuario.setPreferencias(preferencias);
		}else {
			return "USUARIO NO ENCONTRADO";
		}
		try {
			usuarioDao.save(usuario);
		}catch(Exception e){
			return "ERROR: "+e.getMessage();
		}
		return "UPDATED";
	}
	
}
