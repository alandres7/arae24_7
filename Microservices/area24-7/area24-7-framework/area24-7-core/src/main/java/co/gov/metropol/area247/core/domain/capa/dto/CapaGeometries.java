package co.gov.metropol.area247.core.domain.capa.dto;

import java.io.Serializable;
import java.util.List;

import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPolygon;


public class CapaGeometries implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9052117294317155470L;
	
	private Long idCapa;
	
	private List<MarkerPoint> markersPoint;
	
	private List<MarkerPolygon> markersPolygon;
	
	public Long getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(Long idCapa) {
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
