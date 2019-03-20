package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class HorarioLineaResponse {

	@ApiModelProperty(value = "Hora inicio de operación de la línea", required = true)
	private String horaInicioOperacion;

	@ApiModelProperty(value = "Hora fin de operación de la línea", required = true)
	private String horaFinOperacion;

	public HorarioLineaResponse(HorarioLineaMetroDTO horario) {
		this.horaInicioOperacion = horario.getHoraInicioOperacion();
		this.horaFinOperacion = horario.getHoraFinOperacion();
	}

	public HorarioLineaResponse() {
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
