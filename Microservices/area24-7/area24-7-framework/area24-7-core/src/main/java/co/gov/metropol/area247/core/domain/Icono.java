package co.gov.metropol.area247.core.domain;

import java.io.Serializable;

public class Icono implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String rutaLogo;

	private String nombre;

	public Icono() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRutaLogo() {
		return rutaLogo;
	}

	public void setRutaLogo(String rutaLogo) {
		this.rutaLogo = rutaLogo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
