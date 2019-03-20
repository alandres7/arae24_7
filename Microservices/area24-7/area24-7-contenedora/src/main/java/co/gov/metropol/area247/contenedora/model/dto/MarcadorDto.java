package co.gov.metropol.area247.contenedora.model.dto;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.VentanaInformacion;


public class MarcadorDto {

	private Long id;
	private String nombre;
	private String descripcion;
	private Icono icono;
	private String colorBorde;
	private String colorFondo;
	private VentanaInformacion ventanaInformacion;
	private List<CoordenadaDtoM> coordenadas;
	
	
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
	
	public Icono getIcono() {
		return icono;
	}
	
	public void setIcono(Icono icono) {
		this.icono = icono;
	}
	
	public String getColorBorde() {
		return colorBorde;
	}
	
	public void setColorBorde(String colorBorde) {
		this.colorBorde = colorBorde;
	}
	
	public String getColorFondo() {
		return colorFondo;
	}
	
	public void setColorFondo(String colorFondo) {
		this.colorFondo = colorFondo;
	}
	
	public VentanaInformacion getVentanaInformacion() {
		return ventanaInformacion;
	}
	
	public void setVentanaInformacion(VentanaInformacion ventanaInformacion) {
		this.ventanaInformacion = ventanaInformacion;
	}

	public List<CoordenadaDtoM> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<CoordenadaDtoM> coordenadas) {
		this.coordenadas = coordenadas;
	}

}