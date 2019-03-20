package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TareviaEstacionEnciclaDTO extends AbstractDTO{
	/*
	 * Identificador unico de la estacion
	 * */
	@Loggable
	private Long idEstacionEncicla;
	
	/*
	 * Identificador unico de la  zona
	 * */
	@Loggable
	private Long idZona;
	
	/**
	 * Nombre de la estación
	 * */
	@Loggable
	private String nombreEstacionEncicla;
	
	/**
	 * Dirección de la estación
	 * */
	@Loggable
	private String direccionEstacionEncicla;
	
	/**
	 * Descripción de la estación
	 * */
	@Loggable
	private String descripcionEstacionEncicla;
	
	/**
	 * Latitud de la estación
	 * */
	@Loggable
	private Double latitudEstacionEncila;
	
	/**
	 * Longitud de la estación
	 * */
	private Double longitudEstacionEncila;
	
	/**
	 * Estacion activa
	 * */
	private String activaEstacionEncicla;
	
	/**
	 * Tipo de la estación
	 * */
	private String tipoEstacionEncicla;
	
	/**
	 * Capacidad de la estación
	 * */
	private Long capacidadEstacionEncila;
	
	/**
	 * Cantidad de bicicletas
	 * */
	private Long cantidadBicicletas;
	
	/**
	 * Cantidad de lugares
	 * */
	private Long lugares;
	
	/**
	 * Modo estacion
	 * */
	private ModoEstacionDTO modoEstacionDTO;

	@Override
	public TareviaEstacionEnciclaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TareviaEstacionEnciclaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacionEncicla() {
		return idEstacionEncicla;
	}

	public void setIdEstacionEncicla(Long idEstacionEncicla) {
		this.idEstacionEncicla = idEstacionEncicla;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getNombreEstacionEncicla() {
		return nombreEstacionEncicla;
	}

	public void setNombreEstacionEncicla(String nombreEstacionEncicla) {
		this.nombreEstacionEncicla = nombreEstacionEncicla;
	}

	public String getDireccionEstacionEncicla() {
		return direccionEstacionEncicla;
	}

	public void setDireccionEstacionEncicla(String direccionEstacionEncicla) {
		this.direccionEstacionEncicla = direccionEstacionEncicla;
	}

	public String getDescripcionEstacionEncicla() {
		return descripcionEstacionEncicla;
	}

	public void setDescripcionEstacionEncicla(String descripcionEstacionEncicla) {
		this.descripcionEstacionEncicla = descripcionEstacionEncicla;
	}

	public Double getLatitudEstacionEncila() {
		return latitudEstacionEncila;
	}

	public void setLatitudEstacionEncila(Double latitudEstacionEncila) {
		this.latitudEstacionEncila = latitudEstacionEncila;
	}

	public Double getLongitudEstacionEncila() {
		return longitudEstacionEncila;
	}

	public void setLongitudEstacionEncila(Double longitudEstacionEncila) {
		this.longitudEstacionEncila = longitudEstacionEncila;
	}

	public String getActivaEstacionEncicla() {
		return activaEstacionEncicla;
	}

	public void setActivaEstacionEncicla(String activaEstacionEncicla) {
		this.activaEstacionEncicla = activaEstacionEncicla;
	}

	public String getTipoEstacionEncicla() {
		return tipoEstacionEncicla;
	}

	public void setTipoEstacionEncicla(String tipoEstacionEncicla) {
		this.tipoEstacionEncicla = tipoEstacionEncicla;
	}

	public Long getCapacidadEstacionEncila() {
		return capacidadEstacionEncila;
	}

	public void setCapacidadEstacionEncila(Long capacidadEstacionEncila) {
		this.capacidadEstacionEncila = capacidadEstacionEncila;
	}

	public Long getCantidadBicicletas() {
		return cantidadBicicletas;
	}

	public void setCantidadBicicletas(Long cantidadBicicletas) {
		this.cantidadBicicletas = cantidadBicicletas;
	}

	public Long getLugares() {
		return lugares;
	}

	public void setLugares(Long lugares) {
		this.lugares = lugares;
	}

	public ModoEstacionDTO getModoEstacionDTO() {
		return modoEstacionDTO;
	}

	public void setModoEstacionDTO(ModoEstacionDTO modoEstacionDTO) {
		this.modoEstacionDTO = modoEstacionDTO;
	}
	
	
}
