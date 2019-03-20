package co.gov.metropol.area247.repository.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoArchivo;

@Entity
@Table(name = "TSIMAUD_ARCHIVOS", schema = "MOVILIDAD")
public class Archivo extends Entities{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE", nullable = false, length = 200)
	private String nombre;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "D_FECHA_CARGA")
	private Date fechaCarga;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "S_ESTADO", nullable = false, length = 15)
	private EstadoArchivo estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_ARCHIVO", nullable = false)
	private TipoArchivo tipoArchivo;

	@Override
	public Archivo withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public Archivo withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public Archivo setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public Archivo setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
		return this;
	}

	public EstadoArchivo getEstado() {
		return estado;
	}

	public Archivo setEstado(EstadoArchivo estado) {
		this.estado = estado;
		return this;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public Archivo setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
		return this;
	}

}
