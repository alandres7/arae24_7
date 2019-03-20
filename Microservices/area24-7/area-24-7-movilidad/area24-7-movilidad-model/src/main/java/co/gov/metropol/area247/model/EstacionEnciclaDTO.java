package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class EstacionEnciclaDTO extends AbstractDTO{
	
	/**
	 * Identificador unico de la estacion
	 * */
	@Loggable
	private Long idEstacion;
	
	/**
	 * 
	 * */
	@Loggable
	private Long orden;
	
	/**
	 * Nombre de la estacion de encicla
	 * */
	@Loggable
	private String nombre;
	
	/**
	 * Direccion de la estacion de encicla
	 * */
	@Loggable
	private String direccion;
	
	/**
	 * Descripcion de la estacion de encicla
	 * */
	@Loggable
	private String descripcion;
	
	/**
	 * Latitud donde se encuentra ubicada la estacion
	 * */
	@Loggable
	private Double latitud;
	
	/**
	 * Longitud donde se encuentra ubicada la estacion
	 * */
	@Loggable
	private Double longitud;
	
	/**
	 * Tipo de estacion
	 * */
	@Loggable
	private String tipo;

	@Loggable
	private Long capacidad;
	
	/**
	 * Numero de ciclas en la estacion
	 * */
	@Loggable
	private Long ciclas;
	
	/**
	 * Lugares disponibles en la estacion
	 * */
	@Loggable
	private Long lugares;
	
	/**
	 * Imagen de la estacion
	 * */
	@Loggable
	private String imagen;
	
	@Loggable
	private ZonaCiudadEnciclaDTO estacion;
	
	@Override
	public EstacionEnciclaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EstacionEnciclaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public EstacionEnciclaDTO setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
		return this;
	}

	public Long getOrden() {
		return orden;
	}

	public EstacionEnciclaDTO setOrden(Long orden) {
		this.orden = orden;
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public EstacionEnciclaDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDireccion() {
		return direccion;
	}

	public EstacionEnciclaDTO setDireccion(String direccion) {
		this.direccion = direccion;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public EstacionEnciclaDTO setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Double getLatitud() {
		return latitud;
	}

	public EstacionEnciclaDTO setLatitud(Double latitud) {
		this.latitud = latitud;
		return this;
	}

	public Double getLongitud() {
		return longitud;
	}

	public EstacionEnciclaDTO setLongitud(Double longitud) {
		this.longitud = longitud;
		return this;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public EstacionEnciclaDTO setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
		return this;
	}

	public Long getCiclas() {
		return ciclas;
	}

	public EstacionEnciclaDTO setCiclas(Long ciclas) {
		this.ciclas = ciclas;
		return this;
	}

	public Long getLugares() {
		return lugares;
	}

	public EstacionEnciclaDTO setLugares(Long lugares) {
		this.lugares = lugares;
		return this;
	}

	public String getImagen() {
		return imagen;
	}

	public EstacionEnciclaDTO setImagen(String imagen) {
		this.imagen = imagen;
		return this;
	}

	public ZonaCiudadEnciclaDTO getEstacion() {
		return estacion;
	}

	public EstacionEnciclaDTO setEstacion(ZonaCiudadEnciclaDTO estacion) {
		this.estacion = estacion;
		return this;
	}

}
