package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TareviaEstacionEnCiclaResponse {
	
	@ApiModelProperty(value = "Identificador unico de la estacion", required = true)
	private Long id;
	
	@ApiModelProperty(value = "Nombre de la estacion", required = true)
	private String nombre;
	
	@ApiModelProperty(value = "Identificador unico zona", required = true)
	private Long idZona;
	
	@ApiModelProperty(value = "Direccion de la estacion", required = true)
	private String direccion;
	
	@ApiModelProperty(value = "Descripcion de la estacion", required = true)
	private String descripcion;
	
	@ApiModelProperty(value = "Latitud donde se encuentra la estacion", required = true)
	private Double latitud;
	
	@ApiModelProperty(value = "Longitud donde se encuentra la estacion", required = true)
	private Double longitud;
	
	@ApiModelProperty(value = "Estación activa/inactiva", required = true)
	private String activo;
	
	@ApiModelProperty(value = "Tipo de la estación", required = true)
	private String tipo;
	
	@ApiModelProperty(value = "Informacion sobre la capacidad de la estacion", required = true)
	private Long capacidad;
	
	public TareviaEstacionEnCiclaResponse() {
		super();
	}
	
	public TareviaEstacionEnCiclaResponse(TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO) {
		this.id = tareviaEstacionEnciclaDTO.getId();
		this.idZona = tareviaEstacionEnciclaDTO.getIdZona();
		this.nombre = tareviaEstacionEnciclaDTO.getNombreEstacionEncicla();
		this.descripcion = tareviaEstacionEnciclaDTO.getDescripcionEstacionEncicla();
		this.direccion = tareviaEstacionEnciclaDTO.getDireccionEstacionEncicla();
		this.latitud = tareviaEstacionEnciclaDTO.getLatitudEstacionEncila();
		this.longitud = tareviaEstacionEnciclaDTO.getLongitudEstacionEncila();
		this.capacidad = tareviaEstacionEnciclaDTO.getCapacidadEstacionEncila();
		this.activo = tareviaEstacionEnciclaDTO.getActivaEstacionEncicla();
		this.tipo = tareviaEstacionEnciclaDTO.getTipoEstacionEncicla();
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

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
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

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
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

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}
	
	
	
	
}
