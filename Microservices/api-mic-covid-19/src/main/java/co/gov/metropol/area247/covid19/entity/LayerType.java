package co.gov.metropol.area247.covid19.entity;

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
@Table(name = "D247CON_TIPO_CAPA", schema = "CONTENEDOR")
public class LayerType {
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_TIPO_CAPA_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Nombre representativo para el tipo de capa")
	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE")
	private String nombre;

	@JsonProperty
	@ApiModelProperty(notes = "Describe en qué consiste el tipode capa")
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
