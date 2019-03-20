package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;

public class LandMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5377892211549998440L;
	
	private String nombreMunicipio;
	private String tipoSuelo;
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	public String getTipoSuelo() {
		return tipoSuelo;
	}
	public void setTipoSuelo(String tipoSuelo) {
		this.tipoSuelo = tipoSuelo;
	}
	/**
	 * 
	 */
	public LandMessage() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param nombreMunicipio
	 * @param tipoSuelo
	 */
	public LandMessage(String nombreMunicipio, String tipoSuelo) {
		this.nombreMunicipio = nombreMunicipio;
		this.tipoSuelo = tipoSuelo;
	}
	
	
}
