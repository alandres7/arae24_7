package co.gov.metropol.area247.repository.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.ExtensionesDeArchivo;

@Entity
@Table(name = "TSIMAUD_TIPO_ARCHIVO", schema = "MOVILIDAD")
public class TipoArchivo extends Entities {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE", nullable = false, unique = true, length = 40)
	private String nombre;
	
	@Column(name = "S_DESCRIPCION", nullable = false, length = 200)
	private String descripcion;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "S_EXTENSION", nullable = false, length = 6)
	private ExtensionesDeArchivo extension;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "tipoArchivo")
	private Set<EstructuraDeArchivo> estructura = new HashSet<>();

	@Override
	public TipoArchivo withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoArchivo withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoArchivo setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public TipoArchivo setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public ExtensionesDeArchivo getExtension() {
		return extension;
	}

	public TipoArchivo setExtension(ExtensionesDeArchivo extension) {
		this.extension = extension;
		return this;
	}

	public Set<EstructuraDeArchivo> getEstructura() {
		return estructura;
	}

	public TipoArchivo setEstructura(Set<EstructuraDeArchivo> estructuraDeArchivos) {
		this.estructura = estructuraDeArchivos;
		return this;
	}
}
