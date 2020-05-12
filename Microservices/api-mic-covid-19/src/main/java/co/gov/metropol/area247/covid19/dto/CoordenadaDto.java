package co.gov.metropol.area247.covid19.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordenadaDto implements Serializable {
	/**
	 * 
	 */

	private double latitud;
	@JsonProperty("longitud")
	private double longitud;

	public CoordenadaDto() {

	}

	public CoordenadaDto(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

}
