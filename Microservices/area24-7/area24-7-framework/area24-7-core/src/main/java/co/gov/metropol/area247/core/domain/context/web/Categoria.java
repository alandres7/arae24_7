package co.gov.metropol.area247.core.domain.context.web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Categoria implements Serializable{

	private static final long serialVersionUID = 4323393719333924817L;
	
	private Long idCategoria;
	
	private String nombre;
	
	private String nombreTipoCapa;
	
	@JsonIgnore
	private Capa capa;
	
	public Categoria(){
		
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
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

	public Capa getCapa() {
		return capa;
	}

	public void setCapa(Capa capa) {
		this.capa = capa;
	}

}
