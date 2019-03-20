package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.EstacionEnciclaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class EstacionEnciclaResponse {
	
	@ApiModelProperty(value = "Identificador unico de la estacion", required = true)
	private Long id;
	
	@ApiModelProperty(value = "Nombre de la estacion", required = true)
	private String nombre;
	
	@ApiModelProperty(value = "Direccion de la estacion", required = true)
	private String direccion;
	
	@ApiModelProperty(value = "Descripcion de la estacion", required = true)
	private String descripcion;
	
	@ApiModelProperty(value = "Latitud donde se encuentra la estacion", required = true)
	private Double latitud;
	
	@ApiModelProperty(value = "Longitud donde se encuentra la estacion", required = true)
	private Double longitud;
	
	@ApiModelProperty(value = "Informacion sobre la capacidad de la estacion", required = true)
	private Long capacidad;
	
	@ApiModelProperty(value = "Informacion del numero de ciclas", required = true)
	private Long ciclas;
	
	@ApiModelProperty(value = "Informacion de los lugares disponibles", required = true)
	private Long lugares;
	
	@ApiModelProperty(value = "URL de la imagen de la estacion", required = false)
	private String imagen;
	
	public EstacionEnciclaResponse () {
		super();
	}
	
	public EstacionEnciclaResponse(EstacionEnciclaDTO estacionEnciclaDTO) {
		this.id = estacionEnciclaDTO.getId();
		this.nombre = estacionEnciclaDTO.getNombre();
		this.descripcion = estacionEnciclaDTO.getDescripcion();
		this.direccion = estacionEnciclaDTO.getDireccion();
		this.latitud = estacionEnciclaDTO.getLatitud();
		this.longitud = estacionEnciclaDTO.getLongitud();
		this.imagen = estacionEnciclaDTO.getImagen();
		this.capacidad = estacionEnciclaDTO.getCapacidad();
		this.ciclas = estacionEnciclaDTO.getCiclas();
		this.lugares = estacionEnciclaDTO.getLugares();
	}

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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public Long getCiclas() {
		return ciclas;
	}

	public void setCiclas(Long ciclas) {
		this.ciclas = ciclas;
	}

	public Long getLugares() {
		return lugares;
	}

	public void setLugares(Long lugares) {
		this.lugares = lugares;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
