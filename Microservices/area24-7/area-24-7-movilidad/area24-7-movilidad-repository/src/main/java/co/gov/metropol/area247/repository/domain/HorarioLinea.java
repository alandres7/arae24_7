package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_HORARIO_LINEA", schema = "MOVILIDAD")
public class HorarioLinea extends Entities {

	private static final long serialVersionUID = 6747463753516135404L;

	@Column(name = "N_ID_ITEM")
	private Long idHorario;

	@Column(name = "D_HORA_INICIO_OPERACION")
	private String horaInicioOperacion;

	@Column(name = "D_HORA_FIN_OPERACION")
	private String horaFinOperacion;

	@Column(name = "D_HORA_INICIO_PICO_AM")
	private String horaInicioPicoAM;

	@Column(name = "D_HORA_FIN_PICO_AM")
	private String horaFinPicoAM;

	@Column(name = "D_HORA_INICIO_PICO_PM")
	private String horaInicioPicoPM;

	@Column(name = "D_HORA_FIN_PICO_PM")
	private String horaFinPicoPM;

	@Column(name = "S_ACTIVO")
	private String activo;

	@Column(name = "ID_LINEA")
	private Long idLinea;

	@Override
	public HorarioLinea withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public HorarioLinea withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdHorario() {
		return idHorario;
	}

	public HorarioLinea setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
		return this;
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

	public String isActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

}
