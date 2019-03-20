package co.gov.metropol.area247.core.gateway.conversor.dto;

import java.io.Serializable;

public class RequestGeometriesContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808016066005682741L;
	
	private int inSR;
	
	private int outSR;
	
	private GeometriesContainer geometriesContainer;
	
	public RequestGeometriesContainer() {
		// TODO Auto-generated constructor stub
	}

	public RequestGeometriesContainer(int inSR, int outSR, GeometriesContainer geometriesContainer) {
		this.inSR = inSR;
		this.outSR = outSR;
		this.geometriesContainer = geometriesContainer;
	}

	public int getInSR() {
		return inSR;
	}

	public void setInSR(int inSR) {
		this.inSR = inSR;
	}

	public int getOutSR() {
		return outSR;
	}

	public void setOutSR(int outSR) {
		this.outSR = outSR;
	}

	public GeometriesContainer getGeometriesContainer() {
		return geometriesContainer;
	}

	public void setGeometriesContainer(GeometriesContainer geometriesContainer) {
		this.geometriesContainer = geometriesContainer;
	}

}
