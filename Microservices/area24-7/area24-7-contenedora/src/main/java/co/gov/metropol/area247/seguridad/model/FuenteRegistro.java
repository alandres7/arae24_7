package co.gov.metropol.area247.seguridad.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "D247SEG_FUENTE_REGISTRO", schema = "CONTENEDOR")
public class FuenteRegistro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_FUENTE_REGISTRO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Abreviación representativa para la fuente de registro del usaurio, FB para Facebook, GP para Google Plus, AP para Aplicación móvil, AD para el Directorio Activo")
	@NotNull
	@Size(max = 2)
	@Column(name = "S_NOMBRE")
	private String nombre;

	@JsonProperty
	@ApiModelProperty(notes = "Describe en qué consiste la fuente de registro, cómo se llama la fuente de registro abreviada en el nombre")
	@NotNull
	@Size(max = 2000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "ID_ROL", referencedColumnName = "ID")
	private Rol rol;

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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
