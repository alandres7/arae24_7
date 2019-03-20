package co.gov.metropol.area247.services.rest.gtpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class PuntoGtpcWSDTO {
	@JsonProperty(value = "latitud")
	private double  latitud;
	
	@JsonProperty(value = "longitud")
	private double  longitud;

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
