package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.HorarioLineaMetroDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class HorarioLineaMetroWSDTO {

	@JsonProperty(value = "idHorario")
	private Long idHorario;

	@JsonProperty(value = "horaInicioOperacion")
	private String horaInicioOperacion;

	@JsonProperty(value = "horaFinOperacion")
	private String horaFinOperacion;

	@JsonProperty(value = "horaInicioPicoAM")
	private String horaInicioPicoAM;

	@JsonProperty(value = "horaFinPicoAM")
	private String horaFinPicoAM;

	@JsonProperty(value = "horaInicioPicoPM")
	private String horaInicioPicoPM;

	@JsonProperty(value = "horaFinPicoPM")
	private String horaFinPicoPM;

	@JsonProperty(value = "activo")
	private Character activo;

	@JsonProperty(value = "idLinea")
	private String idLinea;

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
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

	public String getHoraInicioPicoAM() {
		return horaInicioPicoAM;
	}

	public void setHoraInicioPicoAM(String horaInicioPicoAM) {
		this.horaInicioPicoAM = horaInicioPicoAM;
	}

	public String getHoraFinPicoAM() {
		return horaFinPicoAM;
	}

	public void setHoraFinPicoAM(String horaFinPicoAM) {
		this.horaFinPicoAM = horaFinPicoAM;
	}

	public String getHoraInicioPicoPM() {
		return horaInicioPicoPM;
	}

	public void setHoraInicioPicoPM(String horaInicioPicoPM) {
		this.horaInicioPicoPM = horaInicioPicoPM;
	}

	public String getHoraFinPicoPM() {
		return horaFinPicoPM;
	}

	public void setHoraFinPicoPM(String horaFinPicoPM) {
		this.horaFinPicoPM = horaFinPicoPM;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public String getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(String idLinea) {
		this.idLinea = idLinea;
	}

	public HorarioLineaMetroDTO getHorarioLineaDTO() {
		HorarioLineaMetroDTO horarioLineaMetroDTO = new HorarioLineaMetroDTO();
		horarioLineaMetroDTO.setIdItem(getIdHorario());
		horarioLineaMetroDTO.setHoraInicioOperacion(getHoraInicioOperacion());
		horarioLineaMetroDTO.setHoraFinOperacion(getHoraFinOperacion());
		horarioLineaMetroDTO.setHoraInicioPicoAM(getHoraInicioPicoAM());
		horarioLineaMetroDTO.setHoraFinPicoAM(getHoraFinPicoAM());
		horarioLineaMetroDTO.setHoraInicioPicoPM(getHoraInicioPicoPM());
		horarioLineaMetroDTO.setHoraFinPicoPM(getHoraFinPicoPM());
		horarioLineaMetroDTO.setActivo(getActivo().toString());

		return horarioLineaMetroDTO;
	}
}
