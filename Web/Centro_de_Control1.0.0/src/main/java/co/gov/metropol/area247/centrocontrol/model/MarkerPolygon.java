package co.gov.metropol.area247.centrocontrol.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.maps.model.LatLng;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkerPolygon {

	private String nombre;
	private String descripcion;
	private Icono icono;
	private String id;
	private String encodedPolygon;
	private List<Point> listaPuntos;
	private boolean markerEntorno;
	private DetalleClima detalleClima;
	
		
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

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEncodedPolygon() {
		return encodedPolygon;
	}
	
	public void setEncodedPolygon(String encodedPolygon) {
		this.encodedPolygon = encodedPolygon;
	}

	public List<Point> getListaPuntos() {
		return listaPuntos;
	}

	public void setListaPuntos(List<Point> listaPuntos) {
		this.listaPuntos = listaPuntos;
	}

	public boolean isMarkerEntorno() {
		return markerEntorno;
	}

	public void setMarkerEntorno(boolean markerEntorno) {
		this.markerEntorno = markerEntorno;
	}

	public DetalleClima getDetalleClima() {
		return detalleClima;
	}

	public void setDetalleClima(DetalleClima detalleClima) {
		this.detalleClima = detalleClima;
	}
			
}