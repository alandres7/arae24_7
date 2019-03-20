package co.gov.metropol.area247.centrocontrol.dto;

import java.io.Serializable;

import co.gov.metropol.area247.centrocontrol.model.PermisoRol;
import co.gov.metropol.area247.centrocontrol.model.Usuario;

public class SecurityDTO implements Serializable{

	private static final long serialVersionUID = 7177480907595821760L;
	
	private Usuario usuario;
	
	private PermisoRol permisosRol;
	
	public SecurityDTO(PermisoRol permisosRol) {
		this.permisosRol = permisosRol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PermisoRol getPermisosRol() {
		return permisosRol;
	}

	public void setPermisosRol(PermisoRol permisosRol) {
		this.permisosRol = permisosRol;
	}
}
