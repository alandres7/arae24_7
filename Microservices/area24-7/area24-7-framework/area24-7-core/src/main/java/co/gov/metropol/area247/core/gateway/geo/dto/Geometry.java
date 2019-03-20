package co.gov.metropol.area247.core.gateway.geo.dto;

import java.util.List;

public class Geometry {
	private double x;
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	private List<List<double[]>> rings;
	
	public Geometry(double x, double y, List<List<double[]>> rings) {
		this.x = x;
		this.y = y;
		this.rings = rings;
	}

	public List<List<double[]>> getRings() {
		return rings;
	}

	public void setRings(List<List<double[]>> rings) {
		this.rings = rings;
	}


}
