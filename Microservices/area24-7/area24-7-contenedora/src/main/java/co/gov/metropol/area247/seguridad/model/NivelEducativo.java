package co.gov.metropol.area247.seguridad.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "D247SEG_NIVEL_EDUCATIVO", schema = "CONTENEDOR")
public class NivelEducativo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_NIVEL_EDUCATIVO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "nombre representativo del nivel de estudios")
	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE")
	private String nombre;

	@JsonProperty
	@ApiModelProperty(notes = "Describe en qué consiste el nivel de estudios")
	@NotNull
	@Size(max = 2000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
