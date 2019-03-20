package co.gov.metropol.area247.model;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class CiclorutaDTO extends AbstractDTO{

	/*
	 * Identificador unico de la estacion
	 * */
	@Loggable
	private Long idItem;
	
	/*
	 * Codigo de la cicloruta
	 * */
	@Loggable
	private String codigo;
	
	/*
	 * Descripcion de la cicloruta
	 * */
	@Loggable
	private String descripcion;
	
	/*
	 * Longitud de la cicloruta
	 * */
	@Loggable
	private Double longitud;
	
	/*
	 * Coordenadas de la cicloruta
	 * */
	@Loggable
	private LineString coordenadas;
	
	/*
	 * Primer punto de la cicloruta
	 * */
	@Loggable
	private Point primerPunto;
	
	/*
	 * Ultimo punto de la cicloruta
	 * */
	@Loggable
	private Point ultimoPunto;
	
	/*
	 * Cicloruta activa
	 * */
	@Loggable
	private String activa;
	
	@Override
	public CiclorutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public CiclorutaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public LineString getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(LineString coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Point getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(Point primerPunto) {
		this.primerPunto = primerPunto;
	}

	public Point getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(Point ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
 
}
