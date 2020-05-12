package co.gov.metropol.area247.covid19.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "D247SEG_DEPARTAMENTO", schema = "CONTENEDOR")
public class Department {
	
	@Id
	@NotNull
	@Column(name="ID", unique=true, precision=10)
	private Long id;
	
	@NotNull
	@Column(name="S_NOMBRE", length=100 )
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="ID_PAIS")
	private Country pais;
	
	@OneToMany(mappedBy="departamento", cascade=CascadeType.ALL)
	private List<Town> municipios;

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

	public Country getPais() {
		return pais;
	}

	public void setPais(Country pais) {
		this.pais = pais;
	}

	public List<Town> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Town> municipios) {
		this.municipios = municipios;
	}
	
	public Department() {}

}
