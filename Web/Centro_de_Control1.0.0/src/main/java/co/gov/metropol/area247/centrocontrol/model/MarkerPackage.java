package co.gov.metropol.area247.centrocontrol.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkerPackage {

	private String idCapa;
	private List<MarkerPoint> markersPoint;
	private List<MarkerPolygon> markersPolygon;
	
	
	public String getIdCapa() {
		return idCapa;
	}
	
	public void setIdCapa(String idCapa) {
		this.idCapa = idCapa;
	}
	
	public List<MarkerPoint> getMarkersPoint() {
		return markersPoint;
	}
	
	public void setMarkersPoint(List<MarkerPoint> markersPoint) {
		this.markersPoint = markersPoint;
	}

	public List<MarkerPolygon> getMarkersPolygon() {
		return markersPolygon;
	}

	public void setMarkersPolygon(List<MarkerPolygon> markersPolygon) {
		this.markersPolygon = markersPolygon;
	}
	
}