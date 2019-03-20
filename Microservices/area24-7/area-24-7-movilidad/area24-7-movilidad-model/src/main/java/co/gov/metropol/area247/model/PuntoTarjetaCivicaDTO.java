package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class PuntoTarjetaCivicaDTO extends AbstractDTO {

	/*
	 * Descripción punto de venta
	 * */
	@Loggable
	private String descripcion;
	
	/*
	 * Tipo punto de venta
	 * */
	@Loggable
	private String tipoPunto;
	
	/**
	 * Latitud donde se encuentra el punto de venta
	 * */
	@Loggable
	private Double latitud;
	
	/**
	 * Longitud donde se encuentra el punto de venta
	 * */
	@Loggable
	private Double longitud;
	
	/**
	 * Activo punto de venta
	 */
	@Loggable
	private String activo;
	
	@Override
	public PuntoTarjetaCivicaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public PuntoTarjetaCivicaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoPunto() {
		return tipoPunto;
	}

	public void setTipoPunto(String tipoPunto) {
		this.tipoPunto = tipoPunto;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	
}
