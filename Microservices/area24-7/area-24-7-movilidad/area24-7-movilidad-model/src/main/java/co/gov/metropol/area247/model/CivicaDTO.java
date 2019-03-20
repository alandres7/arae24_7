package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class CivicaDTO extends AbstractDTO{

	/**
	 * Identificador del punto
	 */
	@Loggable
	private Long idItem;
	
	/**
	 * Descripcion del punto tarjeta civica
	 */
	@Loggable
	private String descripcion;
	
	/**
	 * Tipo de punto de la tarjeta civica
	 */
	@Loggable
	private String tipoPunto;
	
	/**
	 * Latitud del punto de tarjeta civica
	 */
	@Loggable
	private float latitud;
	
	/**
	 * Longitud del punto de tarjeta civica
	 */
	@Loggable
	private float longitud;
	
	/**
	 * Activo el punto de tarjeta civica
	 */
	@Loggable
	private String activo;
	
	@Override
	public CivicaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public CivicaDTO withEnabled(boolean enabled) {
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

	public String getTipoPunto() {
		return tipoPunto;
	}

	public void setTipoPunto(String tipoPunto) {
		this.tipoPunto = tipoPunto;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	

}
