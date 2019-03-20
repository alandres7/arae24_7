package co.gov.metropol.area247.core.domain.capa.dto;

import java.io.Serializable;

public class CapaN implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2398815392394535642L;
	
	private Long id;
	private String nombre;
	private boolean activo;
	private boolean favorito;
	private String rutaIconoCapa;
	private String rutaIconoMarker;
	private String nombreTipoCapa;
	
	public Long getId() {
		return id;
	}
	
	
	public CapaN(Long id, String nombre, boolean activo, boolean favorito, String rutaIconoCapa,
			String rutaIconoMarker, String nombreTipoCapa) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.favorito = favorito;
		this.rutaIconoCapa = rutaIconoCapa;
		this.rutaIconoMarker = rutaIconoMarker;
		this.nombreTipoCapa = nombreTipoCapa;
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
	
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}	
	
	public boolean isFavorito() {
		return favorito;
	}
	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}
	
	public String getRutaIconoCapa() {
		return rutaIconoCapa;
	}


	public void setRutaIconoCapa(String rutaIconoCapa) {
		this.rutaIconoCapa = rutaIconoCapa;
	}


	public String getRutaIconoMarker() {
		return rutaIconoMarker;
	}


	public void setRutaIconoMarker(String rutaIconoMarker) {
		this.rutaIconoMarker = rutaIconoMarker;
	}


	public String getNombreTipoCapa() {
		return nombreTipoCapa;
	}
	public void setNombreTipoCapa(String nombreTipoCapa) {
		this.nombreTipoCapa = nombreTipoCapa;
	}
	/**
	 * 
	 */
	public CapaN() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
