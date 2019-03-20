package co.gov.metropol.area247.centrocontrol.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CapaSubmenu {
	
	private Long  id;     
    private String nombre; 
    private String nombreTipoCapa;
	private String rutaIconoCapa;
	private String urlRef;
	private List<CategoriaSubmenu> categorias;
    
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

	public String getNombreTipoCapa() {
		return nombreTipoCapa;
	}

	public void setNombreTipoCapa(String nombreTipoCapa) {
		this.nombreTipoCapa = nombreTipoCapa;
	}

	public String getRutaIconoCapa() {
		return rutaIconoCapa;
	}

	public void setRutaIconoCapa(String rutaIconoCapa) {
		this.rutaIconoCapa = rutaIconoCapa;
	}

	public String getUrlRef() {
		return urlRef;
	}

	public void setUrlRef(String urlRef) {
		this.urlRef = urlRef;
	}

	public List<CategoriaSubmenu> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaSubmenu> categorias) {
		this.categorias = categorias;
	}
	    
}