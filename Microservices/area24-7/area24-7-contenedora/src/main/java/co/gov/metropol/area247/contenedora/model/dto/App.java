package co.gov.metropol.area247.contenedora.model.dto;

import java.io.Serializable;
import java.util.List;

public class App implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4187481402693656566L;
	
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	private String codigoColor;
	
	private String rutaIcono;
	
	private boolean activo;
	
	private List<RecomendacionDto> recomendaciones;
	
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
	
	public String getRutaIcono() {
		return rutaIcono;
	}

	public void setRutaIcono(String rutaIcono) {
		this.rutaIcono = rutaIcono;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}	

	public List<RecomendacionDto> getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(List<RecomendacionDto> recomendaciones) {
		this.recomendaciones = recomendaciones;
	}

	public App() {
		// TODO Auto-generated constructor stub
	}

}
