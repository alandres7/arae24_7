package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VentanaInformacion {
	
	private Long id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;
    private String enlaceWeb;
    
    /*
	public VentanaInformacion(Long id, String nombre, String descripcion, String rutaImagen, String enlaceWeb) {
    	this.id = id;
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	this.rutaImagen = rutaImagen;
    	this.enlaceWeb = enlaceWeb;
    }*/
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getEnlaceWeb() {
		return enlaceWeb;
	}

	public void setEnlaceWeb(String enlaceWeb) {
		this.enlaceWeb = enlaceWeb;
	}
}