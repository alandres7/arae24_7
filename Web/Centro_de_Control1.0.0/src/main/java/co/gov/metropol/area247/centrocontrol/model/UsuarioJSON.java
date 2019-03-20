package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar los diversos usuarios que usara el centro de control */


@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioJSON {

    private String username;
    private String nickname;
    private String contrasena;
    private String nombreFuenteRegistro;

    
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombreFuenteRegistro() {
		return nombreFuenteRegistro;
	}

	public void setNombreFuenteRegistro(String nombreFuenteRegistro) {
		this.nombreFuenteRegistro = nombreFuenteRegistro;
	}
	
}