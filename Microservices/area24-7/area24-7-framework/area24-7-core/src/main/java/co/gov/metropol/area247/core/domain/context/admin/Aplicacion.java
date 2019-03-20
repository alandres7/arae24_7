package co.gov.metropol.area247.core.domain.context.admin;

import java.io.Serializable;
import java.util.List;

public class Aplicacion implements Serializable{

	private static final long serialVersionUID = -4684917669310961725L;
	
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	private String codigoColor;
	
	private String codigoToggle;
	
	private Boolean activo;
	
	private int radioAccion;
	
	private String rutaIcono;
	
	private List<Capa> capas;
	
	public Aplicacion(){
		
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

	public String getCodigoToggle() {
		return codigoToggle;
	}

	public void setCodigoToggle(String codigoToggle) {
		this.codigoToggle = codigoToggle;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public int getRadioAccion() {
		return radioAccion;
	}

	public void setRadioAccion(int radioAccion) {
		this.radioAccion = radioAccion;
	}

	public String getRutaIcono() {
		return rutaIcono;	
	}

	public void setRutaIcono(String rutaIcono) {
		this.rutaIcono = rutaIcono;
	}

	public List<Capa> getCapas() {
		return capas;
	}

	public void setCapas(List<Capa> capas) {
		this.capas = capas;
	}

}
