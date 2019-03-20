package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.EstacionMetro;

public class TareviaEstacionMetroDTO extends AbstractDTO{
	/*
	 * Identificador unico de la estacion
	 * */
	@Loggable
	private Long idItem;
	
	@Loggable
	private String descripcionEstacionMetro;
	
	/**
	 * Latitud de la estación
	 * */
	@Loggable
	private Double latitudEstacionMetro;
	
	/**
	 * Longitud de la estación
	 * */
	private Double longitudEstacionMetro;
	
	/**
	 * Estacion activa
	 * */
	private String activaEstacionMetro;
	
	/**
	 * Tipo de la estación
	 * */
	private String tipoEstacionMetro;
	
	/**
	 * id modo estacion
	 * */
	private Long idModoEstacion;
	
	public TareviaEstacionMetroDTO(EstacionMetro estacionMetro) {
		this.idItem = estacionMetro.getIdEstacion();
		this.descripcionEstacionMetro =  estacionMetro.getDescripcion();
		this.latitudEstacionMetro = estacionMetro.getLatitud();
		this.longitudEstacionMetro =  estacionMetro.getLongitud();
		this.activaEstacionMetro = estacionMetro.getActivo().toString();
		this.tipoEstacionMetro = estacionMetro.getModoLinea();
		this.idModoEstacion = Long.valueOf(estacionMetro.getIdModoEstacion().indice());
	}

	@Override
	public TareviaEstacionMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TareviaEstacionMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getDescripcionEstacionMetro() {
		return descripcionEstacionMetro;
	}

	public void setDescripcionEstacionMetro(String descripcionEstacionMetro) {
		this.descripcionEstacionMetro = descripcionEstacionMetro;
	}

	public Double getLatitudEstacionMetro() {
		return latitudEstacionMetro;
	}

	public void setLatitudEstacionMetro(Double latitudEstacionMetro) {
		this.latitudEstacionMetro = latitudEstacionMetro;
	}

	public Double getLongitudEstacionMetro() {
		return longitudEstacionMetro;
	}

	public void setLongitudEstacionMetro(Double longitudEstacionMetro) {
		this.longitudEstacionMetro = longitudEstacionMetro;
	}

	public String getActivaEstacionMetro() {
		return activaEstacionMetro;
	}

	public void setActivaEstacionMetro(String activaEstacionMetro) {
		this.activaEstacionMetro = activaEstacionMetro;
	}

	public String getTipoEstacionMetro() {
		return tipoEstacionMetro;
	}

	public void setTipoEstacionMetro(String tipoEstacionMetro) {
		this.tipoEstacionMetro = tipoEstacionMetro;
	}

	public Long getIdModoEstacion() {
		return idModoEstacion;
	}

	public void setIdModoEstacion(Long idModoEstacion) {
		this.idModoEstacion = idModoEstacion;
	}

	
	
	
}
