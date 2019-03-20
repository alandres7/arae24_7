package co.gov.metropol.area247.model;

import java.sql.Time;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class InfoViajesLineaDTO extends AbstractDTO {

	private Long numSalidas;
	private Long frecuencia;
	private Long tiempoVuelta;
	private String horaInicio;
	private String horaFin;
	private LineaMetroDTO lineaDTO;
	private boolean idaVuelta;

	@Override
	public InfoViajesLineaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public InfoViajesLineaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getNumSalidas() {
		return numSalidas;
	}

	public void setNumSalidas(Long numSalidas) {
		this.numSalidas = numSalidas;
	}

	public Long getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Long frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Long getTiempoVuelta() {
		return tiempoVuelta;
	}

	public void setTiempoVuelta(Long tiempoVuelta) {
		this.tiempoVuelta = tiempoVuelta;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public LineaMetroDTO getLineaDTO() {
		return lineaDTO;
	}

	public void setLineaDTO(LineaMetroDTO lineaDTO) {
		this.lineaDTO = lineaDTO;
	}

	public boolean isIdaVuelta() {
		return idaVuelta;
	}

	public void setIdaVuelta(boolean idaVuelta) {
		this.idaVuelta = idaVuelta;
	}

}
