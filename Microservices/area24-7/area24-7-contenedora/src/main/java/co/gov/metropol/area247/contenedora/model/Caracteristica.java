package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "D247CON_CARACTERISTICA", schema = "CONTENEDOR")
public class Caracteristica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	  @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_CARACTERISTICA_ID", allocationSize=1)
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	  private Long id;
	
	@NotNull
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;

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

}
