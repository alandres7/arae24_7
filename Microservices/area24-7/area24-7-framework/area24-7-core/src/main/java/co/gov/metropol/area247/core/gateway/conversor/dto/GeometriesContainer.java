package co.gov.metropol.area247.core.gateway.conversor.dto;

import java.io.Serializable;
import java.util.List;

public class GeometriesContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1230637240564333221L;
	
	private String geometryType;
	
	private List<Geometry> geometries;

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public List<Geometry> getGeometries() {
		return geometries;
	}

	public void setGeometries(List<Geometry> geometries) {
		this.geometries = geometries;
	}
	
	public GeometriesContainer() {
		// TODO Auto-generated constructor stub
	}

	public GeometriesContainer(String geometryType, List<Geometry> geometries) {
		super();
		this.geometryType = geometryType;
		this.geometries = geometries;
	}
	
	
	

}
