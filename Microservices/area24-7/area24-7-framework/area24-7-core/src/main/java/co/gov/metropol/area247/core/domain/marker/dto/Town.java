package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;

import com.google.maps.model.LatLng;

public class Town implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3400414678603539681L;
	
	private Long id;
	private String nombre;
	private String encodePolygon;
	private LatLng zeroPoint;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEncodePolygon() {
		return encodePolygon;
	}
	public void setEncodePolygon(String encodePolygon) {
		this.encodePolygon = encodePolygon;
	}
	public LatLng getZeroPoint() {
		return zeroPoint;
	}
	public void setZeroPoint(LatLng zeroPoint) {
		this.zeroPoint = zeroPoint;
	}
	/**
	 * @param id
	 * @param nombre
	 * @param encodePolygon
	 * @param zeroPoint
	 */
	public Town(Long id, String nombre, String encodePolygon, LatLng zeroPoint) {
		this.id = id;
		this.nombre = nombre;
		this.encodePolygon = encodePolygon;
		this.zeroPoint = zeroPoint;
	}
	/**
	 * 
	 */
	public Town() {
		// TODO Auto-generated constructor stub
	}
	
}
