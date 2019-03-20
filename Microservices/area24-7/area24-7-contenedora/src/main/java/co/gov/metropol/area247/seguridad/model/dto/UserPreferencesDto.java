package co.gov.metropol.area247.seguridad.model.dto;

import java.io.Serializable;

public class UserPreferencesDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4597358962002113027L;
	
	private String preferencias;

	public String getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(String preferencias) {
		this.preferencias = preferencias;
	}

}
