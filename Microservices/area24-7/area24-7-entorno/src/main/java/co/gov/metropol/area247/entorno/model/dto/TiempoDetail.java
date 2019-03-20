package co.gov.metropol.area247.entorno.model.dto;

import java.io.Serializable;

public class TiempoDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6740724043336278456L;
	private String tiempo;
	private String descripcion;
	private String urlIcono;
	private String etiqueta;
	
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
	/**
	 * @param tiempo
	 * @param descripcion
	 * @param urlIcono
	 */
	public TiempoDetail(String tiempo, String descripcion, String urlIcono, String etiqueta) {
		this.tiempo = tiempo;
		this.descripcion = descripcion;
		this.urlIcono = urlIcono;
		this.etiqueta = etiqueta;
	}
	/**
	 * 
	 */
	public TiempoDetail() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
