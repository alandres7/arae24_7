package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleTiempo {
	
	private String  tiempo; 
	private String  descripcion;
	private String  urlIcono; 
	private String  etiqueta;
	
	
	public String getTiempo() {
		return tiempo;
	}
	
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getUrlIcono() {
		return urlIcono;
	}
	
	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}
	
	public String getEtiqueta() {
		return etiqueta;
	}
	
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

}