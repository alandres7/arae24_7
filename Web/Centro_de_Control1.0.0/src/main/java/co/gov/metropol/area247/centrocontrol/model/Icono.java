package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Icono {
	
	private Long id;
	private String rutaLogo;
    private String nombre;
    
    /*
	public Icono(Long id, String rutaLogo, String nombre) {
    	this.id = id;
    	this.rutaLogo = rutaLogo;
        this.nombre = nombre;   
    }*/
       
    public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRutaLogo() {
		return rutaLogo;
	}
	
	public void setRutaLogo(String rutaLogo) {
		this.rutaLogo = rutaLogo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
}