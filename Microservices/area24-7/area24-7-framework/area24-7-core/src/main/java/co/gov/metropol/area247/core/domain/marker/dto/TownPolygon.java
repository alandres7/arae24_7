package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;
import java.util.List;

import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.core.util.GeometryUtil;

public class TownPolygon implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8385452796025833954L;
	
	private String nombreMunicipio;
	private Polygon polygon;
	
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	public List<LatLng> getPolygon() {
		return GeometryUtil.conversorCoordenadasMaps(polygon);
	}
	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	/**
	 * 
	 */
	public TownPolygon() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param nombreMunicipio
	 * @param polygon
	 */
	public TownPolygon(String nombreMunicipio, Polygon polygon) {
		this.nombreMunicipio = nombreMunicipio;
		this.polygon = polygon;
	}
	
	
	
	
}
