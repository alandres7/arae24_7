package co.gov.metropol.area247.seguridad.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RolDto implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5077125797609099846L;

	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Nombre representativo para el rol")
	private String nombre;
	
	@JsonProperty
	@ApiModelProperty(notes = "Describe en qué consiste el rol")
	private String descripcion;
	
	@JsonProperty
	@ApiModelProperty(notes = "Describe si el rol se encuentra activo para su uso o no")
	private boolean activo;

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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public RolDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
