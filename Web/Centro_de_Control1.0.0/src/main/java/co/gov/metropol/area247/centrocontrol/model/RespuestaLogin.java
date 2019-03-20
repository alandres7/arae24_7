package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar la respuesta que obtenemos al momento de Loguearnos con las
    credenciales de un Usuario */


@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaLogin {
	
	private UsuarioJSON usuario;
	private String token;
	
	

	public UsuarioJSON getUsuario() {
		return usuario;
	}
	
	public void setUsuario(UsuarioJSON usuario) {
		this.usuario = usuario;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
    
}