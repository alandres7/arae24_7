package co.gov.metropol.area247.centrocontrol.seguridad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.dto.LoginExternoDto;
import co.gov.metropol.area247.centrocontrol.dto.LoginExternoMapper;
import co.gov.metropol.area247.centrocontrol.model.LoginExterno;
import co.gov.metropol.area247.centrocontrol.model.RespuestaLogin;
import co.gov.metropol.area247.centrocontrol.model.Usuario;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadLoginExternoRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadLoginExternoService;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;

@Service
public class ISeguridadLoginExternoServiceImpl implements ISeguridadLoginExternoService {
	@Autowired // Crea el objeto automaticamente y lo destruye cuando termina la
	// session o transaccion (Inyectar dependencias)
	private CentroControlExchange centroControlExchange;

	@Value("${area247.username}")
	private String usernameContenedora;

	@Value("${area247.password}")
	private String psswrdContenedora;

	@Value("${area247.fuente.registro}")
	private String fuenteRegistroContenedora;
	
	@Value("${access.token}")
	private String token;
	
	@Autowired
	ISeguridadLoginExternoRepository loginExternoDao;

	LoginExterno loginExternoAux;

	@Override
	public String getToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String token;
		if (auth.getPrincipal() instanceof String) {
			try{
			RespuestaLogin respuestaLogin = centroControlExchange.loguinUsuario(usernameContenedora, psswrdContenedora,
					fuenteRegistroContenedora);
				token = respuestaLogin.getToken();
			}catch(Exception e){
				token = this.token;
			}
			return token;
		}
		Usuario usuario = (Usuario) auth.getPrincipal();
		String username = usuario.getUsername();
		LoginExterno loginExternoAux = loginExternoDao.findByUsername(username);
		return loginExternoAux.getToken();
	}

	@Override
	public boolean registrarLoginExterno(LoginExternoDto loginExterno) {
		try {
			loginExternoAux = loginExternoDao.findByUsername(loginExterno.getUsername());
			if (loginExternoAux == null) {
				loginExternoAux = new LoginExterno();
				loginExternoAux = LoginExternoMapper.getEntidad(loginExterno);
			} else {
				loginExternoAux.setToken(loginExterno.getToken());
				loginExternoAux.setFechaAcceso(loginExterno.getFechaAcceso());
			}
			loginExternoDao.save(loginExternoAux);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
