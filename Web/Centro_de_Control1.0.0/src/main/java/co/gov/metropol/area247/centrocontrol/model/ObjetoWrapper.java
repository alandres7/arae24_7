package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.List;

public class ObjetoWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 969807835820136505L;
	
	private List<Objeto> objetos;
	

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

}
