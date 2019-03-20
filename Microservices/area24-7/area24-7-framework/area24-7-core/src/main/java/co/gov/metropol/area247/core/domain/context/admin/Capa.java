package co.gov.metropol.area247.core.domain.context.admin;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Capa implements Serializable {

	private static final long serialVersionUID = 5113498910309670060L;

	private Long id;

	private String nombre;

	private String nombreTipoCapa;

	@JsonIgnore
	private Aplicacion aplicacion;

	private String rutaIconoCapa;
	
	private int flag;

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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
