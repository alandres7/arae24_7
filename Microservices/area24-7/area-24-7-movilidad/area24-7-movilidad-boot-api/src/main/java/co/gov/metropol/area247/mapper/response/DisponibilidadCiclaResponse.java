package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.DisponibilidadCiclaDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class DisponibilidadCiclaResponse {

	@ApiModelProperty(value = "Identificador unico de la estacion", required = true)
	private Long idEstacion;

	@ApiModelProperty(value = "Informacion sobre los puestos disponibles en la estacion", required = true)
	private Long cantidadPuestosDisponibles;

	@ApiModelProperty(value = "Informacion sobre la cantidad de ciclas disponibles ena la estacion", required = true)
	private Long cantidadBicicletasDisponibles;

	public DisponibilidadCiclaResponse() {
		super();
	}

	public DisponibilidadCiclaResponse(DisponibilidadCiclaDTO disponibilidadCiclaDTO) {
		super();
		this.idEstacion = disponibilidadCiclaDTO.getIdEstacionEncicla();
		this.cantidadPuestosDisponibles = disponibilidadCiclaDTO.getPuestosEstacionEncila();
		this.cantidadBicicletasDisponibles = disponibilidadCiclaDTO.getCantidadBicicletas();
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public Long getCantidadPuestosDisponibles() {
		return cantidadPuestosDisponibles;
	}

	public void setCantidadPuestosDisponibles(Long cantidadPuestosDisponibles) {
		this.cantidadPuestosDisponibles = cantidadPuestosDisponibles;
	}

	public Long getCantidadBicicletasDisponibles() {
		return cantidadBicicletasDisponibles;
	}

	public void setCantidadBicicletasDisponibles(Long cantidadBicicletasDisponibles) {
		this.cantidadBicicletasDisponibles = cantidadBicicletasDisponibles;
	}

}
