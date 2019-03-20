package co.gov.metropol.area247.core.domain.capa.dto;

import java.io.Serializable;
import java.util.List;

public class CapaMarkerList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3074153864445549994L;

	private String nombreCapa;

	private String urlIcono;

	private List<CategoriaMarkerList> categorias;

	public String getNombreCapa() {
		return nombreCapa;
	}

	public void setNombreCapa(String nombreCapa) {
		this.nombreCapa = nombreCapa;
	}

	public List<CategoriaMarkerList> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaMarkerList> categorias) {
		this.categorias = categorias;
	}

	public String getUrlIcono() {
		return urlIcono;
	}

	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}

	public CapaMarkerList() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param nombreCapa
	 * @param urlIcono
	 * @param categorias
	 */
	public CapaMarkerList(String nombreCapa, String urlIcono, List<CategoriaMarkerList> categorias) {
		this.nombreCapa = nombreCapa;
		this.urlIcono = urlIcono;
		this.categorias = categorias;
	}

}
