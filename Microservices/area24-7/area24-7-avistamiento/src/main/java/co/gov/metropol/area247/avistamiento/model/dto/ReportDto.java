package co.gov.metropol.area247.avistamiento.model.dto;

import java.io.Serializable;

public class ReportDto implements Serializable {

	private static final long serialVersionUID = 7332872467306230450L;
	
	private Long id;
	
	private String nombre;
	
	private int cantidadAvistamientoAprobado;
	
	private int cantidadAvistamientoPendiente;
	
	private int cantidadAvistamientoRechazado;
	
	private int totalAvistamiento;
	
	public ReportDto() {
		
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadAvistamientoAprobado() {
		return cantidadAvistamientoAprobado;
	}

	public void setCantidadAvistamientoAprobado(int cantidadAvistamientoAprobado) {
		this.cantidadAvistamientoAprobado = cantidadAvistamientoAprobado;
	}

	public int getCantidadAvistamientoPendiente() {
		return cantidadAvistamientoPendiente;
	}

	public void setCantidadAvistamientoPendiente(int cantidadAvistamientoPendiente) {
		this.cantidadAvistamientoPendiente = cantidadAvistamientoPendiente;
	}

	public int getCantidadAvistamientoRechazado() {
		return cantidadAvistamientoRechazado;
	}

	public void setCantidadAvistamientoRechazado(int cantidadAvistamientoRechazado) {
		this.cantidadAvistamientoRechazado = cantidadAvistamientoRechazado;
	}

	public int getTotalAvistamiento() {
		return totalAvistamiento;
	}

	public void setTotalAvistamiento(int totalAvistamiento) {
		this.totalAvistamiento = totalAvistamiento;
	}

}
