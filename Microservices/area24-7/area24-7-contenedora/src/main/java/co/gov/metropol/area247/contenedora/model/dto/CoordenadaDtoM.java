package co.gov.metropol.area247.contenedora.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordenadaDtoM {
	
	private Long  id;    
	private String direccion;
	
	private List<String> coordenadaPunto;
	private List<String> coordenadaPolygon;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<String> getCoordenadaPunto() {
		return coordenadaPunto;
	}

	public void setCoordenadaPunto(List<String> coordenadaPunto) {
		this.coordenadaPunto = coordenadaPunto;
	}

	public List<String> getCoordenadaPolygon() {
		return coordenadaPolygon;
	}

	public void setCoordenadaPolygon(List<String> coordenadaPolygon) {
		this.coordenadaPolygon = coordenadaPolygon;
	}
	      
}