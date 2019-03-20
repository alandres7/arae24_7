package co.gov.metropol.area247.core.domain.capa.dto;

import java.io.Serializable;

public class CategoriaMarkerList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8842034479701436004L;
	
	private String nombreCategoria;
	private String urlIcono;
	private String nombreMarcador;
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public String getUrlIcono() {
		return urlIcono;
	}
	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}
	
	public String getNombreMarcador() {
		return nombreMarcador;
	}
	public void setNombreMarcador(String nombreMarcador) {
		this.nombreMarcador = nombreMarcador;
	}
	/**
	 * 
	 */
	public CategoriaMarkerList() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param nombreCategoria
	 * @param urlIcono
	 * @param nombreMarcador
	 */
	public CategoriaMarkerList(String nombreCategoria, String urlIcono, String nombreMarcador) {
		this.nombreCategoria = nombreCategoria;
		this.urlIcono = urlIcono;
		this.nombreMarcador = nombreMarcador;
	}
	
	

}
