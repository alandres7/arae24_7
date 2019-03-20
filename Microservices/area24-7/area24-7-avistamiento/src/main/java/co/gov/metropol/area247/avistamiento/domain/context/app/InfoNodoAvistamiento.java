package co.gov.metropol.area247.avistamiento.domain.context.app;

import java.io.Serializable;

public class InfoNodoAvistamiento implements Serializable {

	private static final long serialVersionUID = 4959852774727668862L;

	private Long id;
	
	private String nombre;
	
	private String nombreCientifico;
	
	private String descripcion;
	
	private String rutaMultimedia;
	
	private Boolean tieneHijos;
	
	public InfoNodoAvistamiento(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRutaMultimedia() {
		return rutaMultimedia;
	}

	public void setRutaMultimedia(String rutaMultimedia) {
		this.rutaMultimedia = rutaMultimedia;
	}

	public Boolean getTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(Boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

}
