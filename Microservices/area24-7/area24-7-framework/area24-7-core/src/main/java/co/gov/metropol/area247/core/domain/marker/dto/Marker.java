package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;

public class Marker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719029817447273358L;
	
	private Long id;
	
	private String nombre;
	
	private String rutaWebIcono;

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

	public String getRutaWebIcono() {
		return rutaWebIcono;
	}

	public void setRutaWebIcono(String rutaWebIcono) {
		this.rutaWebIcono = rutaWebIcono;
	}

	/**
	 * 
	 */
	public Marker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param nombre
	 * @param rutaWebIcono
	 */
	public Marker(Long id, String nombre, String rutaWebIcono) {
		this.id = id;
		this.nombre = nombre;
		this.rutaWebIcono = rutaWebIcono;
	}
	
}
