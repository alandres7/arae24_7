package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class HorarioLineaMetroDTO extends AbstractDTO {

	
	private Long idHorario;
	
	private String horaInicioOperacion;
	
	private String horaFinOperacion;
	
	private String horaInicioPicoAM;
	
	private String horaFinPicoAM;
	
	private String horaInicioPicoPM;
	
	private String horaFinPicoPM;
	
	private LineaMetroDTO lineaDTO;
	
	private String activo;
	
	private Long idItem;

	@Override
	public HorarioLineaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public HorarioLineaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}
	
	public Long getIdHorario() {
		return idHorario;
	}

	public HorarioLineaMetroDTO setIdHorario(Long idHorario) {
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

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public LineaMetroDTO getLineaDTO() {
		return lineaDTO;
	}

	public void setLineaDTO(LineaMetroDTO lineaDTO) {
		this.lineaDTO = lineaDTO;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	
}
