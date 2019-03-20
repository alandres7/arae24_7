package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class EstacionLineaMetroResponse {

	@ApiModelProperty(value = "Identificador unico de la estacion", required = true)
	private Long idEstacion;

	@ApiModelProperty(value = "Descripcion de la estacion", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Latitud donde se encuentra la estacion", required = true)
	private Double latitud;

	@ApiModelProperty(value = "Longitud donde se encuentra la estacion", required = true)
	private Double longitud;

	@ApiModelProperty(value = "Indica si la estacion se encunetra activa o no", required = true)
	private Character activo;

	@ApiModelProperty(value = "Identificador del modo de transporte al que pertenece la estación", required = true)
	private Long idModoEstacion;

	@ApiModelProperty(value = "Nombre del modo de transporte al que pertenece la estación", required = true)
	private String nombreModoEstacion;

	@ApiModelProperty(value = "Indica si la estación es automática o manual", required = true)
	private String tipoEstacion;

	@ApiModelProperty(value = "Identificador de la línea a la cual se encuentra asociada la estación", required = true)
	private Long idLinea;

	@ApiModelProperty(value = "Descripción de la línea a la cual se encuentra asociada la estación", required = true)
	private String descripcionLinea;

	public EstacionLineaMetroResponse(EstacionLineaMetroDTO estacion) {
		this.idEstacion = estacion.getId();
		this.descripcion = estacion.getDescripcion();
		this.latitud = estacion.getLatitud();
		this.longitud = estacion.getLongitud();
		this.activo = estacion.getActivo();
		if (estacion.getModoEstacionDTO() != null) {
			this.idModoEstacion = (long)estacion.getModoEstacionDTO().indice();
			this.nombreModoEstacion = estacion.getModoEstacionDTO().name();
		}
		this.tipoEstacion = estacion.getTipoEstacion();
		if (estacion.getLineaDTO() != null) {
			this.idLinea = estacion.getLineaDTO().getId();
			this.descripcionLinea = estacion.getLineaDTO().getDescripcion();
		}
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
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

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public Long getIdModoEstacion() {
		return idModoEstacion;
	}

	public void setIdModoEstacion(Long idModoEstacion) {
		this.idModoEstacion = idModoEstacion;
	}

	public String getNombreModoEstacion() {
		return nombreModoEstacion;
	}

	public void setNombreModoEstacion(String nombreModoEstacion) {
		this.nombreModoEstacion = nombreModoEstacion;
	}

	public String getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(String tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	public String getDescripcionLinea() {
		return descripcionLinea;
	}

	public void setDescripcionLinea(String descripcionLinea) {
		this.descripcionLinea = descripcionLinea;
	}

}
