package co.gov.metropol.area247.entorno.model.dto;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;


public class MedicionDto{

	private Long id;
	private Long idCapa;
	private String nombre;
	private String descripcion;
	private String color;
	private String significado;
	private String recomendacion;
	private float escalaInicial;
	private float escalaFinal;
	private Icono icono;
		
	
	public MedicionDto(Long id, Long idCapa, String nombre, String descripcion, String color, String significado,
			String recomendacion, float escalaInicial, float escalaFinal, Icono icono) {
		this.id = id;
		this.idCapa = idCapa;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.color = color;
		this.significado = significado;
		this.recomendacion = recomendacion;
		this.escalaInicial = escalaInicial;
		this.escalaFinal = escalaFinal;
		this.icono = icono;
	}

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
	
	public float getEscalaInicial() {
		return escalaInicial;
	}
	
	public void setEscalaInicial(float escalaInicial) {
		this.escalaInicial = escalaInicial;
	}
	
	public float getEscalaFinal() {
		return escalaFinal;
	}
	
	public void setEscalaFinal(float escalaFinal) {
		this.escalaFinal = escalaFinal;
	}
	
	public Icono getIcono() {
		return icono;
	}
	
	public void setIcono(Icono icono) {
		this.icono = icono;
	}

}
