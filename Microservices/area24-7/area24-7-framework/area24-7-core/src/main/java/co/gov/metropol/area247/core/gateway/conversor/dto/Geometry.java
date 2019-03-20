package co.gov.metropol.area247.core.gateway.conversor.dto;

import java.io.Serializable;

public class Geometry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4348864768795701632L;
	private double  x;
	private double y;
	
	public Geometry() {
		// TODO Auto-generated constructor stub
	}

	public Geometry(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

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
	
	
	
}
