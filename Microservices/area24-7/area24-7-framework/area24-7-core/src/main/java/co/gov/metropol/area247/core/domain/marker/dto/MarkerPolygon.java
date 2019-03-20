package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;

public class MarkerPolygon implements Serializable{

	private static final long serialVersionUID = 3872801270040212891L;
	
	private Long id;
	
	private String encodedPolygon;
	
	public MarkerPolygon(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEncodedPolygon() {
		return encodedPolygon;
	}

	public void setEncodedPolygon(String encodedPolygon) {
		this.encodedPolygon = encodedPolygon;
	}

	/**
	 * @param id
	 * @param encodedPolygon
	 */
	public MarkerPolygon(Long id, String encodedPolygon) {
		this.id = id;
		this.encodedPolygon = encodedPolygon;
	}
	
	
}
