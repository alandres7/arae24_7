package co.gov.metropol.area247.entorno.domain;

import java.io.Serializable;

public class BaseClima implements Serializable{

	private static final long serialVersionUID = 3004787074726465915L;
	
	public String nombre;
	
	public String municipio;
	
	public String estado;
	
	public String recomendacion;
	
	public String icono;
	
	public String descripcion;
	
	public BaseClima(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
