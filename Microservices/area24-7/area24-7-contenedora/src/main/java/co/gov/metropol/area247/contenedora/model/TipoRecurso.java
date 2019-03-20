package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "D247CON_TIPO_RECURSO", schema = "CONTENEDOR")
public class TipoRecurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	  @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_TIPO_RECURSO_ID", allocationSize=1)
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	  private Long id;
	
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
