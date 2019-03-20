package co.gov.metropol.area247.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.EstructuraDeArchivo;
import co.gov.metropol.area247.repository.domain.support.enums.ExtensionesDeArchivo;

public class TipoArchivoDTO extends AbstractDTO{
	
	@NotNull
	@Size(max = 30)
	@Loggable
	private String nombre;
	
	@NotNull
	@Size(max = 200)
	@Loggable
	private String descripcion;
	
	@NotNull
	@Loggable
	private ExtensionesDeArchivo extension;
	
	@Loggable
	private Set<EstructuraDeArchivo> estructura = new HashSet<>(0);

	@Override
	public TipoArchivoDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoArchivoDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoArchivoDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public TipoArchivoDTO setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public ExtensionesDeArchivo getExtension() {
		return extension;
	}

	public TipoArchivoDTO setExtension(ExtensionesDeArchivo extension) {
		this.extension = extension;
		return this;
	}

	public Set<EstructuraDeArchivo> getEstructura() {
		return estructura;
	}

	public TipoArchivoDTO setEstructura(Set<EstructuraDeArchivo> estructuraDeArchivos) {
		this.estructura = estructuraDeArchivos;
		return this;
	}

}
