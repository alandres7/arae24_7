package co.gov.metropol.area247.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoArchivo;

public class ArchivoDTO extends AbstractDTO{
	
	@NotNull
	@Size(max = 200)
	@Loggable
	private String nombre;
	
	@Loggable
	private Date fechaCarga;
	
	@Loggable
	private EstadoArchivo estado;
	
	@NotNull
	@Loggable
	private TipoArchivoDTO tipoArchivo;

	@Override
	public ArchivoDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ArchivoDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public ArchivoDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public ArchivoDTO setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
		return this;
	}

	public EstadoArchivo getEstado() {
		return estado;
	}

	public ArchivoDTO setEstado(EstadoArchivo estado) {
		this.estado = estado;
		return this;
	}

	public TipoArchivoDTO getTipoArchivo() {
		return tipoArchivo;
	}

	public ArchivoDTO setTipoArchivo(TipoArchivoDTO tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
		return this;
	}

}
