package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Clase encargada de almacenar los tipos de 
 * respuesta del aplicativo huellas. */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoRespuestaHuella {

	private Long id;
	private String nombre;
	private String descripcion;
	private String modeloRespuesta;
		
	
	public TipoRespuestaHuella(Long id, String nombre, String descripcion, String modeloRespuesta) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.modeloRespuesta = modeloRespuesta;
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
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getModeloRespuesta() {
		return modeloRespuesta;
	}

	public void setModeloRespuesta(String modeloRespuesta) {
		this.modeloRespuesta = modeloRespuesta;
	}
									
}