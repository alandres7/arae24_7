package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class FormConsumoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570164791367514820L;

	private long id;
	
	private String nombre;
	
	private String descripcion;
	
	private boolean decision;
	
	private boolean global;
	
	private Set<PreguntaConsumoDto> preguntasConsumo =  new HashSet<>();;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isDecision() {
		return decision;
	}

	public void setDecision(boolean decision) {
		this.decision = decision;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}
	

	public Set<PreguntaConsumoDto> getPreguntasConsumo() {
		return preguntasConsumo;
	}

	public void setPreguntasConsumo(Set<PreguntaConsumoDto> preguntasConsumo) {
		this.preguntasConsumo = preguntasConsumo;
	}

	/**
	 * 
	 */
	public FormConsumoDto() {
		// TODO Auto-generated constructor stub
	}
	
}
