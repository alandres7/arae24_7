package co.gov.metropol.area247.core.gateway.posconsumo.dto;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = -4920769601914789812L;

	private double latitude;

	private double longitude;
	
	/**
	 * 
	 */
	public Point() {
		// TODO Auto-generated constructor stub
	}

	public Point(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
