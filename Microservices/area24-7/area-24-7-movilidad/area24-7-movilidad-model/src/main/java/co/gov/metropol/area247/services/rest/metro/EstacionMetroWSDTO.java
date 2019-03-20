package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.Utils;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class EstacionMetroWSDTO {

	@JsonProperty(value = "idEstacion")
	private Long idEstacion;

	@JsonProperty(value = "codigo")
	private String codigo;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "latitud")
	private Double latitud;

	@JsonProperty(value = "longitud")
	private Double longitud;

	@JsonProperty(value = "activo")
	private char activo;

	@JsonProperty(value = "modoEstacion")
	private ModoTransporteWSDTO modoEstacion;

	@JsonProperty(value = "tipoEstacion")
	private String tipoEstacion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(String tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public char getActivo() {
		return activo;
	}

	public void setActivo(char activo) {
		this.activo = activo;
	}

	public ModoTransporteWSDTO getModoEstacion() {
		return modoEstacion;
	}

	public void setModoEstacion(ModoTransporteWSDTO modoEstacion) {
		this.modoEstacion = modoEstacion;
	}

	public EstacionLineaMetroDTO getEstacionLineaMetroDTO() {
		EstacionLineaMetroDTO estacionLineaMetroDTO = new EstacionLineaMetroDTO();
		estacionLineaMetroDTO.setIdEstacion(getIdEstacion());
		estacionLineaMetroDTO.setCodigo(getCodigo());
		estacionLineaMetroDTO.setDescripcion(getDescripcion());
		estacionLineaMetroDTO.setLatitud(getLatitud());
		estacionLineaMetroDTO.setLongitud(getLongitud());
		estacionLineaMetroDTO.setTipoEstacion(getTipoEstacion());
		estacionLineaMetroDTO.setActivo(getActivo());
		estacionLineaMetroDTO.setModoEstacionDTO(!Utils.isNull(getModoEstacion())
				? ModoRecorrido.valueOf(getModoEstacion().getModoEstacionDTO().getId().intValue()) : null
		);
		return estacionLineaMetroDTO;
	}

}
