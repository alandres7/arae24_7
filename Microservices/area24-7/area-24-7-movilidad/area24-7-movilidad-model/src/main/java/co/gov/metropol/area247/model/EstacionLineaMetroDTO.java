package co.gov.metropol.area247.model;

import javax.validation.constraints.NotNull;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;

public class EstacionLineaMetroDTO extends AbstractDTO {

	private Long idEstacion;

	private String codigo;

	private String descripcion;

	private Double latitud;

	private Double longitud;

	@NotNull
	private ModoRecorrido modoEstacionDTO;

	private String tipoEstacion;

	private LineaMetroDTO lineaDTO;

	private Character activo;
	
	private Long sequencia;

	public EstacionLineaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EstacionLineaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public EstacionLineaMetroDTO setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
		return this;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(String tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public ModoRecorrido getModoEstacionDTO() {
		return modoEstacionDTO;
	}

	public void setModoEstacionDTO(ModoRecorrido modoEstacionDTO) {
		this.modoEstacionDTO = modoEstacionDTO;
	}

	public LineaMetroDTO getLineaDTO() {
		return lineaDTO;
	}

	public void setLineaDTO(LineaMetroDTO lineaDTO) {
		this.lineaDTO = lineaDTO;
	}

	public Long getSequencia() {
		return sequencia;
	}

	public void setSequencia(Long sequencia) {
		this.sequencia = sequencia;
	}

}
