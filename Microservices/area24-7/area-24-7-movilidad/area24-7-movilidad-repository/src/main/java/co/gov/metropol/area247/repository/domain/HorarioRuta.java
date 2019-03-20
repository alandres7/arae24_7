package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_HORARIO_RUTA", schema = "MOVILIDAD")
public class HorarioRuta extends Entities {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "N_FUENTE_DATOS")
	private int fuenteDatos;
	
	@Column(name = "D_HORA_INICIO_OPERACION")
	private String horaInicioOperacion;
	
	@Column(name = "D_HORA_FIN_OPERACION")
	private String horaFinOperacion;
	
	@Column(name = "D_HORA_INICIO_PICO_AM")
	private String horaInicioPicoAm;
	
	@Column(name = "D_HORA_FIN_PICO_AM")
	private String horaFinPicoAm;
	
	@Column(name = "D_HORA_INCIO_PICO_PM")
	private String horaInicioPicoPm;
	
	@Column(name = "D_HORA_FIN_PICO_PM ")
	private String horaFinPicoPm;
		
	@Column(name = "S_ACTIVO ")
	private String activo;
	
	@Column(name = "ID_RUTA ")
	private Long idRuta;
	
	@Override
	public HorarioRuta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public HorarioRuta withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
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

	public String getHoraInicioPicoAm() {
		return horaInicioPicoAm;
	}

	public void setHoraInicioPicoAm(String horaInicioPicoAm) {
		this.horaInicioPicoAm = horaInicioPicoAm;
	}

	public String getHoraFinPicoAm() {
		return horaFinPicoAm;
	}

	public void setHoraFinPicoAm(String horaFinPicoAm) {
		this.horaFinPicoAm = horaFinPicoAm;
	}

	public String getHoraInicioPicoPm() {
		return horaInicioPicoPm;
	}

	public void setHoraInicioPicoPm(String horaInicioPicoPm) {
		this.horaInicioPicoPm = horaInicioPicoPm;
	}

	public String getHoraFinPicoPm() {
		return horaFinPicoPm;
	}

	public void setHoraFinPicoPm(String horaFinPicoPm) {
		this.horaFinPicoPm = horaFinPicoPm;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}
	
}
