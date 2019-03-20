package co.gov.metropol.area247.services.rest.gtpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class CoordenadasGtpcWSDTO {
	@JsonProperty(value = "gtype")
	private int gtype;
	
	@JsonProperty(value = "srid")
	private int srid;
	
	@JsonProperty(value = "elemInfo")
	private double[] elemInfo;
	
	@JsonProperty(value = "ordinates")
	private double[] ordinates;

	
	public double[] getElemInfo() {
		return elemInfo;
	}

	public void setElemInfo(double[] elemInfo) {
		this.elemInfo = elemInfo;
	}

	public double[] getOrdinates() {
		return ordinates;
	}

	public void setOrdinates(double[] ordinates) {
		this.ordinates = ordinates;
	}

	public int getGtype() {
		return gtype;
	}

	public void setGtype(int gtype) {
		this.gtype = gtype;
	}

	public int getSrid() {
		return srid;
	}

	public void setSrid(int srid) {
		this.srid = srid;
	}
	
	
}
