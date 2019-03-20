package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;
import java.util.List;

public class MarkerDTO implements Serializable {

	private static final long serialVersionUID = -5215382121049569765L;

	private long idCapa;

	private List<MarkerPoint> markersPoint;

	public MarkerDTO() {

	}

	public long getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(long idCapa) {
		this.idCapa = idCapa;
	}

	public List<MarkerPoint> getMarkersPoint() {
		return markersPoint;
	}

	public void setMarkersPoint(List<MarkerPoint> markersPoint) {
		this.markersPoint = markersPoint;
	}
}
