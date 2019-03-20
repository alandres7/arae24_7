package co.gov.metropol.area247.core.domain.context.web;

import java.io.Serializable;

public class NodoRaiz implements Serializable{
	
	private static final long serialVersionUID = 4593631176380414463L;

	private Long id;
	
	private String pregunta;
	
	private String descripcion;
	
	private String rutaMultimedia;
	
	private Boolean tieneHijos;
	
	public NodoRaiz(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}


	public String getRutaMultimedia() {
		return rutaMultimedia;
	}

	public void setRutaMultimedia(String rutaMultimedia) {
		this.rutaMultimedia = rutaMultimedia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(Boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

}
