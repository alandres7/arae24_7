package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.DataType;

@Entity
@Table(name = "TSIMAUD_ESTRUCTURA_ARCHIVO", schema = "MOVILIDAD")
public class EstructuraDeArchivo extends Entities{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE", nullable = false, length = 30)
	private String nombre;
	
	@Column(name = "S_DESCRIPCION", nullable = false, length = 200)
	private String descripcion;
	
	@Column(name = "N_POSICION_INICIAL")
	private Long pocisionInicial;
	
	@Column(name = "N_POSICION_FINAL")
	private Long posicionFinal;
	
	@Column(name = "N_ORDEN", nullable = false)
	private Long orden;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "S_TIPO_DE_DATO", nullable = false, length = 15)
	private DataType tipoDato;
	
	@Column(name = "N_LONGITUD", nullable = false)
	private Long length;
	
	@Column(name = "B_REQUERIDO")
    private boolean requerido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_ARCHIVO", nullable = false)
	private TipoArchivo tipoArchivo;

	@Override
	public EstructuraDeArchivo withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EstructuraDeArchivo withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public EstructuraDeArchivo setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public EstructuraDeArchivo setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Long getPocisionInicial() {
		return pocisionInicial;
	}

	public EstructuraDeArchivo setPocisionInicial(Long pocisionInicial) {
		this.pocisionInicial = pocisionInicial;
		return this;
	}

	public Long getPosicionFinal() {
		return posicionFinal;
	}

	public EstructuraDeArchivo setPosicionFinal(Long posicionFinal) {
		this.posicionFinal = posicionFinal;
		return this;
	}

	public Long getOrden() {
		return orden;
	}

	public EstructuraDeArchivo setOrden(Long orden) {
		this.orden = orden;
		return this;
	}

	public DataType getTipoDato() {
		return tipoDato;
	}

	public EstructuraDeArchivo setTipoDato(DataType tipoDato) {
		this.tipoDato = tipoDato;
		return this;
	}

	public Long getLength() {
		return length;
	}

	public EstructuraDeArchivo setLength(Long length) {
		this.length = length;
		return this;
	}

	public boolean isRequerido() {
		return requerido;
	}

	public EstructuraDeArchivo setRequerido(boolean requerido) {
		this.requerido = requerido;
		return this;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public EstructuraDeArchivo setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
		return this;
	}
}
