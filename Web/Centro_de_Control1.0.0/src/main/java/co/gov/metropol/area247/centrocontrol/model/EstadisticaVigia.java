package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EstadisticaVigia {

	private String nombreCapa;
	private int cantidadAprobados;
	private int cantidadPendientes;		
	private int cantidadRechazados;
	
	
	public String getNombreCapa() {
		return nombreCapa;
	}
	
	public void setNombreCapa(String nombreCapa) {
		this.nombreCapa = nombreCapa;
	}
	
	public int getCantidadAprobados() {
		return cantidadAprobados;
	}
	
	public void setCantidadAprobados(int cantidadAprobados) {
		this.cantidadAprobados = cantidadAprobados;
	}
	
	public int getCantidadPendientes() {
		return cantidadPendientes;
	}
	
	public void setCantidadPendientes(int cantidadPendientes) {
		this.cantidadPendientes = cantidadPendientes;
	}
	
	public int getCantidadRechazados() {
		return cantidadRechazados;
	}
	
	public void setCantidadRechazados(int cantidadRechazados) {
		this.cantidadRechazados = cantidadRechazados;
	}	
		
}