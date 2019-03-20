package co.gov.metropol.area247.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.DataType;

public class EstructuraDeArchivoDTO extends AbstractDTO {
	
	@NotNull
	@Size(max = 30)
	@Loggable
	private String nombre;
	
	@NotNull
	@Size(max = 200)
	@Loggable
	private String descripcion;
	
	@Loggable
	private Long pocisionInicial;
	
	@Loggable
	private Long posicionFinal;
	
	@Loggable
	private Long orden;
	
	@NotNull
	@Loggable
	private DataType tipoDato;
	
	@Loggable
	private Long length;
	
	@Loggable
    private boolean requerido;
	
	@Loggable
    private TipoArchivoDTO tipoArchivo;

	@Override
	public EstructuraDeArchivoDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EstructuraDeArchivoDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public EstructuraDeArchivoDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public EstructuraDeArchivoDTO setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Long getPocisionInicial() {
		return pocisionInicial;
	}

	public EstructuraDeArchivoDTO setPocisionInicial(Long pocisionInicial) {
		this.pocisionInicial = pocisionInicial;
		return this;
	}

	public Long getPosicionFinal() {
		return posicionFinal;
	}

	public EstructuraDeArchivoDTO setPosicionFinal(Long posicionFinal) {
		this.posicionFinal = posicionFinal;
		return this;
	}

	public Long getOrden() {
		return orden;
	}

	public EstructuraDeArchivoDTO setOrden(Long orden) {
		this.orden = orden;
		return this;
	}

	public DataType getTipoDato() {
		return tipoDato;
	}

	public EstructuraDeArchivoDTO setTipoDato(DataType tipoDato) {
		this.tipoDato = tipoDato;
		return this;
	}

	public Long getLength() {
		return length;
	}

	public EstructuraDeArchivoDTO setLength(Long length) {
		this.length = length;
		return this;
	}

	public boolean isRequerido() {
		return requerido;
	}

	public EstructuraDeArchivoDTO setRequerido(boolean requerido) {
		this.requerido = requerido;
		return this;
	}

	public TipoArchivoDTO getTipoArchivo() {
		return tipoArchivo;
	}

	public EstructuraDeArchivoDTO setTipoArchivo(TipoArchivoDTO tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
		return this;
	}
}
