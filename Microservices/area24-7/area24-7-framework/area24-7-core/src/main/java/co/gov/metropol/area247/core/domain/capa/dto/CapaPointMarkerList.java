package co.gov.metropol.area247.core.domain.capa.dto;

import java.io.Serializable;
import java.util.List;

import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;

public class CapaPointMarkerList implements Serializable {

	private static final long serialVersionUID = -5215382121049569765L;

	private long idCapa;

	private List<MarkerPoint> markersPoint;

	public CapaPointMarkerList() {

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
