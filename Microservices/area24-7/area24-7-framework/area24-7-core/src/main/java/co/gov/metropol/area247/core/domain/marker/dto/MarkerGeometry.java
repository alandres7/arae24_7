package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.core.util.GeometryUtil;

public class MarkerGeometry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9052117294317155470L;
	
	private Long marcadorId;
	
	private Point coordenadaPunto;
	
	private String encondedLinestring;
	
	private String encodedPolygon;

	public Long getMarcadorId() {
		return marcadorId;
	}

	public void setMarcadorId(Long marcadorId) {
		this.marcadorId = marcadorId;
	}
	
	public List<?> getCoordenadaPunto() {
		return GeometryUtil.conversorCoordenada(coordenadaPunto);
	}

	public void setCoordenadaPunto(Point coordenadaPunto) {
		this.coordenadaPunto = coordenadaPunto;
	}

	public String getEncondedLinestring() {
		return encondedLinestring;
	}

	public void setEncondedLinestring(String encondedLinestring) {
		this.encondedLinestring = encondedLinestring;
	}

	public String getEncodedPolygon() {
		return encodedPolygon;
	}

	public void setEncodedPolygon(String encodedPolygon) {
		this.encodedPolygon = encodedPolygon;
	}
	
}
