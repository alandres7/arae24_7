package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = -4920769601914789812L;

	private double lat;

	private double lng;
	
	/**
	 * 
	 */
	public Point() {
		// TODO Auto-generated constructor stub
	}

	public Point(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
