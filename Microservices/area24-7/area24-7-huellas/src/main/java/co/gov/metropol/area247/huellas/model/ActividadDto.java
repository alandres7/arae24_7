package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ActividadDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570164791367514820L;
	
	@JsonProperty
	@ApiModelProperty(value = "ID de la actividad determinada")
	private long id;
	
	@JsonProperty
	@ApiModelProperty(value = "nombre de la actividad determinada")
	private String nombre;
	
	@JsonProperty
	@ApiModelProperty(value = "Descripción de la actividad determinada")
	private String descripcion;
	
	@JsonProperty
	@ApiModelProperty(value = "Determina el orden en que aparecerán las actividades en la encuesta")
	private int orden;
	
	@JsonProperty
	@ApiModelProperty(value = "Determina si la actividad tiene una pregunta de decisión")
	private boolean decision;
	
	@JsonProperty
	@ApiModelProperty("ID de la capa a la que está asociada la actividad")
	private long idCapa;
	
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

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public boolean isDecision() {
		return decision;
	}

	public void setDecision(boolean decision) {
		this.decision = decision;
	}

	public long getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(long idCapa) {
		this.idCapa = idCapa;
	}

	/**
	 * 
	 */
	public ActividadDto() {
		
	}
	
	/**
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param orden
	 */
	public ActividadDto(long id, String nombre, String descripcion, int orden) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.orden = orden;
	}
	
}
