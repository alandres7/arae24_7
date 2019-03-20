package co.gov.metropol.area247.centrocontrol.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MarcadorAvist {

	private String idAvistamiento;
	private String idCapa;
	private String idCategoria;
	private Point point;
	private Icono imageAvistamiento;
	
	
	public String getIdAvistamiento() {
		return idAvistamiento;
	}
	
	public void setIdAvistamiento(String idAvistamiento) {
		this.idAvistamiento = idAvistamiento;
	}
	
	public String getIdCapa() {
		return idCapa;
	}
	
	public void setIdCapa(String idCapa) {
		this.idCapa = idCapa;
	}
	
	public String getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public Icono getImageAvistamiento() {
		return imageAvistamiento;
	}
	
	public void setImageAvistamiento(Icono imageAvistamiento) {
		this.imageAvistamiento = imageAvistamiento;
	}

}