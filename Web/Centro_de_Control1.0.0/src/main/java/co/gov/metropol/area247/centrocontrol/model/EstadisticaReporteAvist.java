package co.gov.metropol.area247.centrocontrol.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EstadisticaReporteAvist {

	private Long id;
	private String nombre;
	private int cantidadAvistamientoAprobado;
	private int cantidadAvistamientoPendiente;	
	private int cantidadAvistamientoRechazado;
	private int totalAvistamiento;
	
			
	/*public EstadisticaReporteAvist(String nombre, int cantidadAvistamientoAprobado,
		int cantidadAvistamientoPendiente,int cantidadAvistamientoRechazado) {
		this.nombre = nombre;
		this.cantidadAvistamientoAprobado = cantidadAvistamientoAprobado;
		this.cantidadAvistamientoPendiente = cantidadAvistamientoPendiente;
		this.cantidadAvistamientoRechazado = cantidadAvistamientoRechazado;
	}*/
	
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