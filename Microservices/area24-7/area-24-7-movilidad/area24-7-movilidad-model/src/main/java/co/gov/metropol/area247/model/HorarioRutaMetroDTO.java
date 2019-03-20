package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class HorarioRutaMetroDTO extends AbstractDTO {

	private Long idHorario;

	private String horaInicioOperacion;

	private String horaFinOperacion;

	private String horaInicioPicoAm;

	private String horaFinPicoAm;

	private String horaInicioPicoPm;

	private String horaFinPicoPm;

	private RutaMetroDTO rutaDTO;

	private String activo;

	private Long idItem;

	private int fuenteDatos;

	@Override
	public HorarioRutaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public HorarioRutaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdHorario() {
		return idHorario;
	}

	public HorarioRutaMetroDTO setIdHorario(Long idHorario) {
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

	public RutaMetroDTO getRutaDTO() {
		return rutaDTO;
	}

	public void setRutaDTO(RutaMetroDTO rutaDTO) {
		this.rutaDTO = rutaDTO;
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

}
