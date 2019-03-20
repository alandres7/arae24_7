package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkerPoint {
	
	private String nombre;
	private String descripcion;
	private String nombreCientifico;
	private String idMarker;
	private String urlImagen;
	private String rutaWebIcono;
	private Point point;
	private String datosEstacion;
	private boolean markerEntorno;
	
		
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
					
	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

	public String getIdMarker() {
		return idMarker;
	}
	
	public void setIdMarker(String idMarker) {
		this.idMarker = idMarker;
	}
			
	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	public String getRutaWebIcono() {
		return rutaWebIcono;
	}

	public void setRutaWebIcono(String rutaWebIcono) {
		this.rutaWebIcono = rutaWebIcono;
	}

	public Point getPoint() {
		return point;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}

	public String getDatosEstacion() {
		return datosEstacion;
	}

	public void setDatosEstacion(String datosEstacion) {
		this.datosEstacion = datosEstacion;
	}

	public boolean isMarkerEntorno() {
		return markerEntorno;
	}

	public void setMarkerEntorno(boolean markerEntorno) {
		this.markerEntorno = markerEntorno;
	}
		
}