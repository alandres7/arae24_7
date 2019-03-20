package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;
import java.util.List;

import co.gov.metropol.area247.core.domain.capa.dto.CategoriaMarkerList;

public class MarkersLand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5377892211549998440L;
	
	private String nombreMunicipio;
	private List<CategoriaMarkerList> categorias;
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	public List<CategoriaMarkerList> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<CategoriaMarkerList> categorias) {
		this.categorias = categorias;
	}
	/**
	 * 
	 */
	public MarkersLand() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param nombreMunicipio
	 * @param categorias
	 */
	public MarkersLand(String nombreMunicipio, List<CategoriaMarkerList> categorias) {
		this.nombreMunicipio = nombreMunicipio;
		this.categorias = categorias;
	}
	
	
	
	
}
