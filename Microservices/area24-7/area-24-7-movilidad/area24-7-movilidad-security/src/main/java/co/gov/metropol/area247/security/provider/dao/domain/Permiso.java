package co.gov.metropol.area247.security.provider.dao.domain;

import co.gov.metropol.area247.security.provider.dao.domain.enums.Privilegios;
import co.gov.metropol.area247.util.Utils;

public class Permiso {
	
	private Privilegios privilegios;
	
	private Recurso recurso;

	public Privilegios getPrivilegios() {
		return privilegios;
	}

	public Permiso setPrivilegios(Privilegios privilegios) {
		this.privilegios = privilegios;
		return this;
	}

	public Recurso getRecurso() {
		if(Utils.isNull(this.recurso)){
			this.recurso = new Recurso();
		}
		return recurso;
	}

	public Permiso setRecurso(Recurso recurso) {
		this.recurso = recurso;
		return this;
	}
}
