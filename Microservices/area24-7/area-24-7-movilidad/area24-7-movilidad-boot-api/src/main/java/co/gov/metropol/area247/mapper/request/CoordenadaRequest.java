package co.gov.metropol.area247.mapper.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;

/**
 * Informacion de las coordenadas del usuario
 * */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CoordenadaRequest {
	
	@ApiModelProperty(value = "Latitud donde esta parado el usuario", required = true)
	private double latitud;
	
	@ApiModelProperty(value = "Longitud donde se encuentra parado el usuario", required = true)
	private double longitud;
	
	@ApiModelProperty(value = "Radio de busqueda en kilometros", required = true)
	private float radio;

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

	public Float getRadio() {
		return radio;
	}

	public void setRadio(Float radio) {
		this.radio = radio;
	}
	
	public boolean validate() {
		return false;
	}

}
