package co.gov.metropol.area247.core.domain.marker.dto;

public class MarkerPoint {

	private Long idMarker;

	private Point point;

	private String rutaWebIcono;

	public MarkerPoint() {

	}

	public Long getIdMarker() {
		return idMarker;
	}

	public void setIdMarker(Long idMarker) {
		this.idMarker = idMarker;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getRutaWebIcono() {
		return rutaWebIcono;
	}

	public void setRutaWebIcono(String rutaWebIcono) {
		this.rutaWebIcono = rutaWebIcono;
	}

	/**
	 * @param idMarker
	 * @param point
	 * @param rutaWebIcono
	 */
	public MarkerPoint(Long idMarker, Point point, String rutaWebIcono) {
		this.idMarker = idMarker;
		this.point = point;
		this.rutaWebIcono = rutaWebIcono;
	}
	
	

}
