package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Clase encargada de almacenar las diversas especies de un avistamiento. */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Especie {

	private Long id;
	private String nombre;
	private Icono icono;
	private Long idCategoria;
	
	
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
	
	public Icono getIcono() {
		return icono;
	}
	
	public void setIcono(Icono icono) {
		this.icono = icono;
	}
	
	public Long getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
							
}