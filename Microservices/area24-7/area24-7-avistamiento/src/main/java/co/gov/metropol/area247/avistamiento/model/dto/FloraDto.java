package co.gov.metropol.area247.avistamiento.model.dto;

import java.io.Serializable;

public class FloraDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5631216034569636718L;
	
	private String nombreCientifico;
	private String nombreComun;
	private String tipoArbol;
	private String foto;
	private double coordenadaX;
	private double coordenadaY;
	
	public FloraDto(String nombreCientifico, String nombreComun, String tipoArbol, String foto, double coordenadaX,
			double coordenadaY) {
		this.nombreCientifico = nombreCientifico;
		this.nombreComun = nombreComun;
		this.tipoArbol = tipoArbol;
		this.foto = foto;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
	}

	public FloraDto() {
		// TODO Auto-generated constructor stub
	}

	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

	public String getNombreComun() {
		return nombreComun;
	}

	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}

	public String getTipoArbol() {
		return tipoArbol;
	}

	public void setTipoArbol(String tipoArbol) {
		this.tipoArbol = tipoArbol;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public double getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public double getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(double coordenadaY) {
		this.coordenadaY = coordenadaY;
	}	
	
}
