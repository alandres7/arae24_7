package co.gov.metropol.area247.centrocontrol.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase encargada de almacenar los diversos menús que usara el centro de
 * control
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Aplicacion {

	private Long id;
	private String nombre;
	private String descripcion;
	private String codigoColor;
	private Boolean activo;
	private Boolean favorito;
	private Icono icono;
	private int radioAccion;
	private Date ultimaActualizacion;
	private Recomendacion recomendacion;

   	/*
    public Aplicacion(Long id, String nombre, String codigoColor, Boolean activo, Boolean favorito, 
			          int radioAccion, Date ultimaActualizacion) { 
		 this.id = id; 
		 this.nombre = nombre; 
		 this.codigoColor = codigoColor; 
	     this.activo = activo; 
	     this.favorito = favorito; 
	     this.radioAccion = radioAccion;
	     this.ultimaActualizacion = ultimaActualizacion; 
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

	public String getCodigoColor() {
		return codigoColor;
	}

	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}
		
	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public int getRadioAccion() {
		return radioAccion;
	}

	public void setRadioAccion(int radioAccion) {
		this.radioAccion = radioAccion;
	}
	
	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public Recomendacion getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}
		
}