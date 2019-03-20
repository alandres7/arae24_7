package co.gov.metropol.area247.centrocontrol.model;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase encargada de almacenar los diversos menús que usara el centro de
 * control
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuItem {

	private Long id;
	private String nombre;
	private String descripcion;
	private String codigoColor;		
	private Boolean activo;
	private Icono icono;
	private Recomendacion recomendacion;
	private List<CapaSubmenu> capas;
	
	

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
	
	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}
		
	public Recomendacion getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}

	public List<CapaSubmenu> getCapas() {
		return capas;
	}

	public void setCapas(List<CapaSubmenu> capas) {
		this.capas = capas;
	}

}