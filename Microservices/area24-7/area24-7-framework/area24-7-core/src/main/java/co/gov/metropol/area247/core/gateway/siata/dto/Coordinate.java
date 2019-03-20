package co.gov.metropol.area247.core.gateway.siata.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8724159818763893359L;
	
	@JsonProperty("latitud")
	private double latitud;
	@JsonProperty("longitud")
	private double longitud;
	
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
	/**
	 * @param latitud
	 * @param longitud
	 */
	public Coordinate(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}
	/**
	 * 
	 */
	public Coordinate() {
		// TODO Auto-generated constructor stub
	}

}
