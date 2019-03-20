package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar los diversos arboles de desicion que usara el centro de control */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArbolDecision {
	
	private Long  id;     
    private String nombre; 
    private String descripcion;
    private Boolean activo;
    private String idCapa;
    private String idCategoria;
    private String rutaCapaCateg;
    
		
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(String idCapa) {
		this.idCapa = idCapa;
	}
		
	public String getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getRutaCapaCateg() {
		return rutaCapaCateg;
	}

	public void setRutaCapaCateg(String rutaCapaCateg) {
		this.rutaCapaCateg = rutaCapaCateg;
	}
							
}