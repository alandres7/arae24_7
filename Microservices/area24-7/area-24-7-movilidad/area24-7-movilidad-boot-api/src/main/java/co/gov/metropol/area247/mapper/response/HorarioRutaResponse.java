package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.HorarioRutaDTO;
import co.gov.metropol.area247.model.HorarioRutaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class HorarioRutaResponse {
	@ApiModelProperty(value = "Hora inicio de operación de la ruta", required = true)
	private String horaInicioOperacion;

	@ApiModelProperty(value = "Hora fin de operación de la ruta", required = true)
	private String horaFinOperacion;

	public HorarioRutaResponse() {
		this.horaInicioOperacion = null;
		this.horaFinOperacion = null;
	}

	public HorarioRutaResponse(HorarioRutaDTO horarioRutaDTO) {
		this.horaInicioOperacion = horarioRutaDTO.getHoraInicioOperacion();
		this.horaFinOperacion = horarioRutaDTO.getHoraFinOperacion();
	}
	
	public HorarioRutaResponse(HorarioRutaMetroDTO horarioRutaMetroDTO) {
		this.horaInicioOperacion = horarioRutaMetroDTO.getHoraInicioOperacion();
		this.horaFinOperacion = horarioRutaMetroDTO.getHoraFinOperacion();
	}

	public String getHoraInicioOperacion() {
		return horaInicioOperacion;
	}

	public void setHoraInicioOperacion(String horaInicioOperacion) {
		this.horaInicioOperacion = horaInicioOperacion;
	}

	public String getHoraFinOperacion() {
		return horaFinOperacion;
	}

	public void setHoraFinOperacion(String horaFinOperacion) {
		this.horaFinOperacion = horaFinOperacion;
	}

}
