package co.gov.metropol.area247.contenedora.model.dto;

import java.io.Serializable;

public class IconoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 418863408078718841L;

	private Long id;
	
	private String nombre;

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
	
	public IconoDto() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param nombre
	 */
	public IconoDto(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	
}
