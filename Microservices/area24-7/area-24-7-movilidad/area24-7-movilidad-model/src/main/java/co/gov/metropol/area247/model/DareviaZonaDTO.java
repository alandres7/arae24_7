package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class DareviaZonaDTO extends AbstractDTO {

	/*
	 * Identificador unico de la zona
	 * */
	@Loggable
	private Long idZona;
	
	/**
	 * Nombre de la estación de zona
	 * */
	@Loggable
	private String nombreZona;
	
	/**
	 * Descripción de la zona
	 * */
	@Loggable
	private String descripcionZona;
	
	@Override
	public DareviaZonaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public DareviaZonaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}
	
	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getDescripcionZona() {
		return descripcionZona;
	}

	public void setDescripcionZona(String descripcionZona) {
		this.descripcionZona = descripcionZona;
	}

	
}
