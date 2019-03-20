package co.gov.metropol.area247.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoArchivo;

public class CargaArchivoDTO extends AbstractDTO{
	
	@Loggable
	private String nombre;
	
	@Loggable
	private Date fechaCarga;
	
	@Loggable
	private EstadoArchivo estado;
	
	@Loggable
	private TipoArchivoDTO tipoArchivo;
	
	@Loggable
	private MultipartFile archivo;

	@Override
	public CargaArchivoDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public CargaArchivoDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public CargaArchivoDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public CargaArchivoDTO setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
		return this;
	}

	public EstadoArchivo getEstado() {
		return estado;
	}

	public CargaArchivoDTO setEstado(EstadoArchivo estado) {
		this.estado = estado;
		return this;
	}

	public TipoArchivoDTO getTipoArchivo() {
		return tipoArchivo;
	}

	public CargaArchivoDTO setTipoArchivo(TipoArchivoDTO tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
		return this;
	}

	public MultipartFile getArchivo() {
		return archivo;
	}

	public CargaArchivoDTO setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
		return this;
	}
	
	public ArchivoDTO getArchivoDTO() {
		ArchivoDTO archivoDTO = new ArchivoDTO();
		archivoDTO.setNombre(this.archivo.getName());
		archivoDTO.setEstado(EstadoArchivo.PENDIENTE);
		archivoDTO.setFechaCarga(new Date());
		archivoDTO.setTipoArchivo(this.tipoArchivo);
		return archivoDTO;
	}

}
