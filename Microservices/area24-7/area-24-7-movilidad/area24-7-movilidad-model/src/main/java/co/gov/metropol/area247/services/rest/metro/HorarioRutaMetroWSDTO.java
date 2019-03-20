package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.model.HorarioRutaMetroDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class HorarioRutaMetroWSDTO {

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

	@JsonProperty(value = "idRuta")
	private String idRuta;

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

	public String getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}

	public HorarioRutaMetroDTO getHorarioRutaDTO() {
		HorarioRutaMetroDTO horarioRutaMetroDTO = new HorarioRutaMetroDTO();
		horarioRutaMetroDTO.setIdItem(getIdHorario());
		horarioRutaMetroDTO.setHoraInicioOperacion(getHoraInicioOperacion());
		horarioRutaMetroDTO.setHoraFinOperacion(getHoraFinOperacion());
		horarioRutaMetroDTO.setHoraInicioPicoAm(getHoraInicioPicoAM());
		horarioRutaMetroDTO.setHoraFinPicoAm(getHoraFinPicoAM());
		horarioRutaMetroDTO.setHoraInicioPicoPm(getHoraInicioPicoPM());
		horarioRutaMetroDTO.setHoraFinPicoPm(getHoraFinPicoPM());
		horarioRutaMetroDTO.setActivo(getActivo().toString());
		horarioRutaMetroDTO.setFuenteDatos(TipoViaje.METRO);

		return horarioRutaMetroDTO;
	}

}
