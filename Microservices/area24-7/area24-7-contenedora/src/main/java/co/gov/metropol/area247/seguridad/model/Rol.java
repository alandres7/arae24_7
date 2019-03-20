package co.gov.metropol.area247.seguridad.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "D247SEG_ROL", schema = "CONTENEDOR")
public class Rol implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_ROL_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@JsonIgnore
	@ApiModelProperty(notes = "Nombre representativo para el rol")
	@Column(name = "S_NOMBRE")
	@Size(max = 100)
	private String nombre;
	
	@JsonIgnore
	@ApiModelProperty(notes = "Describe en qué consiste el rol")
	@Column(name = "S_DESCRIPCION")
	@Size(max = 2000)
	private String descripcion;
	
	@JsonIgnore
	@ApiModelProperty(notes = "Describe si el rol se encuentra activo para su uso o no")
	@Column(name = "N_ACTIVO")
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

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
