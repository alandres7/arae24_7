package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.List;

public class PermisoRolWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 969807835820136505L;
	
	private List<PermisoRolVista> permisosRol;
	

	public List<PermisoRolVista> getPermisosRol() {
		return permisosRol;
	}

	public void setPermisosRol(List<PermisoRolVista> permisosRol) {
		this.permisosRol = permisosRol;
	}

}
