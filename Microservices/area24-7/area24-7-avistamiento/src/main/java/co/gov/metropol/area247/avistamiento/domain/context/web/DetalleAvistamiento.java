package co.gov.metropol.area247.avistamiento.domain.context.web;

import java.io.Serializable;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.core.domain.marker.dto.Point;

public class DetalleAvistamiento implements Serializable {

	private static final long serialVersionUID = 7952318638362229430L;

	private Long idAvistamiento;

	private Long idCapa;

	private Long idCategoria;

	private Point point;

	private Icono imageAvistamiento;

	private String nombreAvistamiento;

	private String descripcionAvistamiento;
	
	private int tieneHistoria;

	public DetalleAvistamiento() {

	}

	public Long getIdAvistamiento() {
		return idAvistamiento;
	}

	public void setIdAvistamiento(Long idAvistamiento) {
		this.idAvistamiento = idAvistamiento;
	}

	public Long getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(Long idCapa) {
		this.idCapa = idCapa;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreAvistamiento() {
		return nombreAvistamiento;
	}

	public void setNombreAvistamiento(String nombreAvistamiento) {
		this.nombreAvistamiento = nombreAvistamiento;
	}

	public String getDescripcionAvistamiento() {
		return descripcionAvistamiento;
	}

	public void setDescripcionAvistamiento(String descripcionAvistamiento) {
		this.descripcionAvistamiento = descripcionAvistamiento;
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

	public int getTieneHistoria() {
		return tieneHistoria;
	}

	public void setTieneHistoria(int tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}

}
