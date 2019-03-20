package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleClima {
	
	private String  municipio; 
	private String  temperatura;
	private List<DetalleTiempo>  tiempoDetails;
	
	
	public String getMunicipio() {
		return municipio;
	}
	
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public String getTemperatura() {
		return temperatura;
	}
	
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public List<DetalleTiempo> getTiempoDetails() {
		return tiempoDetails;
	}

	public void setTiempoDetails(List<DetalleTiempo> tiempoDetails) {
		this.tiempoDetails = tiempoDetails;
	}
		
}