package co.gov.metropol.area247.centrocontrol.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase encargada de almacenar los diversos Medicones que usara el aplicativo mi Entorno
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Medicion {

	private Long id;
	private Long idCapa;	
	private String nombre;		
	private String descripcion;	
	private String color;
	private String significado;
	private String recomendacion;
	private String escalaInicial;
	private String escalaFinal;
	private Icono icono;
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCapa() {
		return idCapa;
	}
	
	public void setIdCapa(Long idCapa) {
		this.idCapa = idCapa;
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
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getSignificado() {
		return significado;
	}
	
	public void setSignificado(String significado) {
		this.significado = significado;
	}
	
	public String getRecomendacion() {
		return recomendacion;
	}
	
	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}
	
	public String getEscalaInicial() {
		return escalaInicial;
	}
	
	public void setEscalaInicial(String escalaInicial) {
		this.escalaInicial = escalaInicial;
	}
	
	public String getEscalaFinal() {
		return escalaFinal;
	}
	
	public void setEscalaFinal(String escalaFinal) {
		this.escalaFinal = escalaFinal;
	}
	
	public Icono getIcono() {
		return icono;
	}
	
	public void setIcono(Icono icono) {
		this.icono = icono;
	}

}