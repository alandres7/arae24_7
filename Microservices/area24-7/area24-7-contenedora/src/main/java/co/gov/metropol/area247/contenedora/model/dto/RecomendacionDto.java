package co.gov.metropol.area247.contenedora.model.dto;

import java.io.Serializable;

public class RecomendacionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5073579783956259623L;
	
	private String id;
	private String nombre;
	private String descripcion;
	private String tipo;
	private String idAplicacion;
	
	
	public RecomendacionDto(Long id, String nombre, String descripcion, String tipo, Long idAplicacion) {
		this.id = id.toString();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.idAplicacion = idAplicacion.toString();
	}
	
	public RecomendacionDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
				
}
