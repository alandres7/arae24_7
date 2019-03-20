package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class HorarioRutaDTO extends AbstractDTO{
	
	private Long idItem;
	
	private int fuenteDatos;
	
	private String horaInicioOperacion;
	
	private String horaFinOperacion;
	
	private String horaInicioPicoAm;
	
	private String horaFinPicoAm;
	
	private String horaInicioPicoPm;
	
	private String horaFinPicoPm;
	
	private String activo;
	
	private Long idRuta;
	
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

	@Override
	public HorarioRutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public HorarioRutaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return null;
	}

}
