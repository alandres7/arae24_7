package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Clase encargada de almacenar las diversas recursos que se usaran en los reportes de vigias. */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Afectacion {

	private Long id;
	private String nombre;
	private String descripcion;
	private RecursoVigia recurso;
	private AutoridadCompetente autoridad;
	
	
	
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
	
	public RecursoVigia getRecurso() {
		return recurso;
	}
	
	public void setRecurso(RecursoVigia recurso) {
		this.recurso = recurso;
	}
	
	public AutoridadCompetente getAutoridad() {
		return autoridad;
	}
	
	public void setAutoridad(AutoridadCompetente autoridad) {
		this.autoridad = autoridad;
	}
								
}