package co.gov.metropol.area247.entorno.domain;

import java.io.Serializable;

public class Aire extends BaseClima implements Serializable{

	private static final long serialVersionUID = -1824676260323155994L;

	public String fecha;
	
	private String hora;
	
	public Aire(){
		
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

}
