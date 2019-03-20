package co.gov.metropol.area247.core.domain.context.web;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Capa implements Serializable {

	private static final long serialVersionUID = 5113498910309670060L;

	private Long id;

	private NodoRaiz nodoRaiz;

	private String nombre;

	private String nombreTipoCapa;

	private String rutaIconoCapa;

	private boolean contieneHistoria;

	@JsonIgnore
	private Aplicacion aplicacion;

	private List<Categoria> categorias;

	public Capa() {

	}

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

	public String getRutaIconoCapa() {
		return rutaIconoCapa;
	}

	public void setRutaIconoCapa(String rutaIconoCapa) {
		this.rutaIconoCapa = rutaIconoCapa;
	}

	public String getNombreTipoCapa() {
		return nombreTipoCapa;
	}

	public void setNombreTipoCapa(String nombreTipoCapa) {
		this.nombreTipoCapa = nombreTipoCapa;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public boolean isContieneHistoria() {
		return contieneHistoria;
	}

	public void setContieneHistoria(boolean contieneHistoria) {
		this.contieneHistoria = contieneHistoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public NodoRaiz getNodoRaiz() {
		return nodoRaiz;
	}

	public void setNodoRaiz(NodoRaiz nodoRaiz) {
		this.nodoRaiz = nodoRaiz;
	}

}
