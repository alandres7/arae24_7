package co.gov.metropol.area247.entorno.domain;

import java.io.Serializable;

public class Agua extends BaseClima implements Serializable {

	private static final long serialVersionUID = -3190341793000013535L;
	
	private String fecha;
	
	private String hora;
	
	private String barrio;
	
	private String cuenca;

	public Agua(){
		
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

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getCuenca() {
		return cuenca;
	}

	public void setCuenca(String cuenca) {
		this.cuenca = cuenca;
	}
	
}
