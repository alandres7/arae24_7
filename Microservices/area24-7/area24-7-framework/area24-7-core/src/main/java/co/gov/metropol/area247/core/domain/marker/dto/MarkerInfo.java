package co.gov.metropol.area247.core.domain.marker.dto;

import java.io.Serializable;

public class MarkerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5802587536024418263L;
	
	private String nombre;
	
	private String descripcion;
	
	private String nombreMunicipio;
	
	private String direccion;
	
	private String rutaImagen;
	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
		
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
			
}
