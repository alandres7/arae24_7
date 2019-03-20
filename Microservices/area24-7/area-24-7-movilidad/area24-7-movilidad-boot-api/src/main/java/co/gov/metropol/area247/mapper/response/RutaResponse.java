package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.RutaDTO;
import co.gov.metropol.area247.repository.domain.support.enums.RutaType;
import io.swagger.annotations.ApiModelProperty;

/**
 * Respuesta a los posibles viajes dada a una coordenada
 * */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RutaResponse {
	
	@ApiModelProperty(value = "Identificador del punto a consultar", required = true)
	private Long id;
	
	@ApiModelProperty(value = "Nombre del posible viaje", required = true)
	private String nombre;
	
	@ApiModelProperty(value = "Descripcion del posible viaje", required = false)
	private String descripcion;
	
	@ApiModelProperty(value = "Latitud donde se encuentra el viaje", required = true)
	private Double latitud;
	
	@ApiModelProperty(value = "Longitud donde se encuentra el viaje", required = true)
	private Double longitud;
	
	@ApiModelProperty(value = "Indica el tipo de viaje", required = true)
	private RutaType tipoViaje;
	
	@ApiModelProperty(value = "Distancia desde el punto actual al punto de llegada", required = true)
	private Double distancia;
	
	public RutaResponse() {
		super();
	}
	
	public RutaResponse(RutaDTO rutaDTO) {
		this.id = rutaDTO.getId();
		this.descripcion = rutaDTO.getDescripcion();
		this.latitud = rutaDTO.getLatitud();
		this.longitud = rutaDTO.getLongitud();
		this.nombre = rutaDTO.getNombre();
		this.tipoViaje = rutaDTO.getTipoViaje();
		this.distancia = rutaDTO.getDistacia();
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

	public RutaType getTipoViaje() {
		return tipoViaje;
	}

	public void setTipoViaje(RutaType tipoViaje) {
		this.tipoViaje = tipoViaje;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

}
