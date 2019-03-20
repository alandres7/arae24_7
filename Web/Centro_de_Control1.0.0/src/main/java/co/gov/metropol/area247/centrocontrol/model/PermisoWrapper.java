package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.List;

public class PermisoWrapper implements Serializable{

	
	private static final long serialVersionUID = 969807835820136505L;
	
	private List<PermisoUsuarioVista> permisosUsuario;

	public List<PermisoUsuarioVista> getPermisosUsuario() {
		return permisosUsuario;
	}

	public void setPermisosUsuario(List<PermisoUsuarioVista> permisosUsuario) {
		this.permisosUsuario = permisosUsuario;
	}

}
