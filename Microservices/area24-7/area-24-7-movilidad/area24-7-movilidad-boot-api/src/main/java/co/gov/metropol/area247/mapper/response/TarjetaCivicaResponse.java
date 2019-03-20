package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TarjetaCivicaResponse {
	
	@ApiModelProperty(value = "Identificador del punto de venta", required = true)
	private Long idPunto;
	
	@ApiModelProperty(value = "Descripción punto de venta", required = true)
	private String descripcion;
	
	@ApiModelProperty(value = "Tipo punto de venta", required = true)
	private String tipoPunto;
	
	@ApiModelProperty(value = "Latitud donde se encuentra el punto de venta", required = true)
	private Double latitud;
	
	@ApiModelProperty(value = "Longitud donde se encuentra el punto de venta", required = true)
	private Double longitud;
	
	@ApiModelProperty(value = "Activo punto de venta", required = true)
	private String activo;
	
	public TarjetaCivicaResponse(PuntoTarjetaCivicaDTO tarjetaCivcaDTO) {
		this.idPunto = tarjetaCivcaDTO.getId();
		this.descripcion = tarjetaCivcaDTO.getDescripcion();
		this.tipoPunto = tarjetaCivcaDTO.getTipoPunto();
		this.latitud = tarjetaCivcaDTO.getLatitud();
		this.longitud = tarjetaCivcaDTO.getLongitud();
		this.activo = tarjetaCivcaDTO.getActivo();
	}

	public Long getIdPunto() {
		return idPunto;
	}

	public void setIdPunto(Long idPunto) {
		this.idPunto = idPunto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoPunto() {
		return tipoPunto;
	}

	public void setTipoPunto(String tipoPunto) {
		this.tipoPunto = tipoPunto;
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

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	
}
