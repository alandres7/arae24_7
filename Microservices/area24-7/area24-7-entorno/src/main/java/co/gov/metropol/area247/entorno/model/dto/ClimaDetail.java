package co.gov.metropol.area247.entorno.model.dto;

import java.io.Serializable;
import java.util.List;

public class ClimaDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -67894567519397295L;
	private String municipio;
	private String nombreEstacion;
	private String temperatura;
	private String temperaturaMinima;
	private String temperaturaMaxima;
	private List<TiempoDetail> tiempoDetails;	
	/**
	 * @param municipio
	 * @param temperatura
	 * @param tiempoDetails
	 */
	public ClimaDetail(String municipio, String temperatura, String temperaturaMinima,
			String temperaturaMaxima, List<TiempoDetail> tiempoDetails) {
		this.municipio = municipio;
		this.temperatura = temperatura;
		this.tiempoDetails = tiempoDetails;
		this.temperaturaMinima = temperaturaMinima;
		this.temperaturaMaxima = temperaturaMaxima;
	}
	/**
	 * 
	 */
	public ClimaDetail() {
		// 
	}
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
	public List<TiempoDetail> getTiempoDetails() {
		return tiempoDetails;
	}
	public void setTiempoDetails(List<TiempoDetail> tiempoDetails) {
		this.tiempoDetails = tiempoDetails;
	}
	public String getTemperaturaMinima() {
		return temperaturaMinima;
	}
	public void setTemperaturaMinima(String temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}
	public String getTemperaturaMaxima() {
		return temperaturaMaxima;
	}
	public void setTemperaturaMaxima(String temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}
	public String getNombreEstacion() {
		return nombreEstacion;
	}
	public void setNombreEstacion(String nombreEstacion) {
		this.nombreEstacion = nombreEstacion;
	}
		
}
