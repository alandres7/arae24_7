package co.gov.metropol.area247.seguridad.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "D247SEG_PAIS", schema = "CONTENEDOR")
public class Pais implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name="ID", unique= true, precision=10)
	private Long id;
	
	@NotNull
	@Column(name="S_NOMBRE", unique=true)
	private String nombre;
	
	@OneToMany(mappedBy="pais", cascade=CascadeType.ALL)
	private List<Departamento> departamentos;

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

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public Pais() {}

}
