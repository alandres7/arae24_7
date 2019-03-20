package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class FrecuenciaRutaMetroDTO extends AbstractDTO {

	private Long idFrecuencia;

	private Double frecuenciaMinimaPicoAm;

	private Double frecuenciaMinimaPicoPm;

	private Double frecuenciaMinimaValleDiurno;

	private Double frecuenciaMinimaValleNocturno;

	private Double intervaloMaximoPicoAm;

	private Double intervaloMaximoPicoPm;

	private Double intervaloMaximoValleDiurno;

	private Double intervaloMaximoValleNocturno;

	private TipoEstadoRegistroDTO tipoEstadoRegistroDTO;

	private RutaMetroDTO rutaMetroDTO;

	@Override
	public FrecuenciaRutaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public FrecuenciaRutaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdFrecuencia() {
		return idFrecuencia;
	}

	public FrecuenciaRutaMetroDTO setIdFrecuencia(Long idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
		return this;
	}

	public Double getFrecuenciaMinimaPicoAm() {
		return frecuenciaMinimaPicoAm;
	}

	public void setFrecuenciaMinimaPicoAm(Double frecuenciaMinimaPicoAm) {
		this.frecuenciaMinimaPicoAm = frecuenciaMinimaPicoAm;
	}

	public Double getFrecuenciaMinimaPicoPm() {
		return frecuenciaMinimaPicoPm;
	}

	public void setFrecuenciaMinimaPicoPm(Double frecuenciaMinimaPicoPm) {
		this.frecuenciaMinimaPicoPm = frecuenciaMinimaPicoPm;
	}

	public Double getIntervaloMaximoPicoAm() {
		return intervaloMaximoPicoAm;
	}

	public void setIntervaloMaximoPicoAm(Double intervaloMaximoPicoAm) {
		this.intervaloMaximoPicoAm = intervaloMaximoPicoAm;
	}

	public Double getIntervaloMaximoPicoPm() {
		return intervaloMaximoPicoPm;
	}

	public void setIntervaloMaximoPicoPm(Double intervaloMaximoPicoPm) {
		this.intervaloMaximoPicoPm = intervaloMaximoPicoPm;
	}

	public Double getFrecuenciaMinimaValleDiurno() {
		return frecuenciaMinimaValleDiurno;
	}

	public void setFrecuenciaMinimaValleDiurno(Double frecuenciaMinimaValleDiurno) {
		this.frecuenciaMinimaValleDiurno = frecuenciaMinimaValleDiurno;
	}

	public Double getFrecuenciaMinimaValleNocturno() {
		return frecuenciaMinimaValleNocturno;
	}

	public void setFrecuenciaMinimaValleNocturno(Double frecuenciaMinimaValleNocturno) {
		this.frecuenciaMinimaValleNocturno = frecuenciaMinimaValleNocturno;
	}

	public Double getIntervaloMaximoValleDiurno() {
		return intervaloMaximoValleDiurno;
	}

	public void setIntervaloMaximoValleDiurno(Double intervaloMaximoValleDiurno) {
		this.intervaloMaximoValleDiurno = intervaloMaximoValleDiurno;
	}

	public Double getIntervaloMaximoValleNocturno() {
		return intervaloMaximoValleNocturno;
	}

	public void setIntervaloMaximoValleNocturno(Double intervaloMaximoValleNocturno) {
		this.intervaloMaximoValleNocturno = intervaloMaximoValleNocturno;
	}

	public TipoEstadoRegistroDTO getTipoEstadoRegistroDTO() {
		return tipoEstadoRegistroDTO;
	}

	public void setTipoEstadoRegistroDTO(TipoEstadoRegistroDTO tipoEstadoRegistroDTO) {
		this.tipoEstadoRegistroDTO = tipoEstadoRegistroDTO;
	}

	public RutaMetroDTO getRutaMetroDTO() {
		return rutaMetroDTO;
	}

	public void setRutaMetroDTO(RutaMetroDTO rutaMetroDTO) {
		this.rutaMetroDTO = rutaMetroDTO;
	}

}
