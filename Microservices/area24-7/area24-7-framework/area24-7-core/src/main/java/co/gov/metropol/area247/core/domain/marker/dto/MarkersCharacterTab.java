package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;
import java.util.List;

import co.gov.metropol.area247.core.domain.capa.dto.CapaMarkerList;

public class MarkersCharacterTab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8887535720253672772L;
	
	private String nombreMunicipio;
	
	private List<CapaMarkerList> capas;
	
	

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	
	

	public List<CapaMarkerList> getCapas() {
		return capas;
	}

	public void setCapas(List<CapaMarkerList> capas) {
		this.capas = capas;
	}

	/**
	 * 
	 */
	public MarkersCharacterTab() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param capas
	 */
	public MarkersCharacterTab(List<CapaMarkerList> capas) {
		this.capas = capas;
	}

	/**
	 * @param nombreMunicipio
	 * @param capasMarkerList
	 */
	public MarkersCharacterTab(String nombreMunicipio, List<CapaMarkerList> capas) {
		this.nombreMunicipio = nombreMunicipio;
		this.capas = capas;
	}
}
