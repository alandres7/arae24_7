package co.gov.metropol.area247.filter;

import java.util.Date;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoArchivo;

public class ArchivoFiltroDTO {
	
	@Loggable
	private Long tipoArchivoId;
	
	@Loggable
	private Date fechaInicio;
	
	@Loggable
	private Date fechaFinal;
	
	@Loggable
	private EstadoArchivo estadoArchivo;

	public Long getTipoArchivoId() {
		return tipoArchivoId;
	}

	public void setTipoArchivoId(Long tipoArchivoId) {
		this.tipoArchivoId = tipoArchivoId;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public EstadoArchivo getEstadoArchivo() {
		return estadoArchivo;
	}

	public void setEstadoArchivo(EstadoArchivo estadoArchivo) {
		this.estadoArchivo = estadoArchivo;
	}
}
