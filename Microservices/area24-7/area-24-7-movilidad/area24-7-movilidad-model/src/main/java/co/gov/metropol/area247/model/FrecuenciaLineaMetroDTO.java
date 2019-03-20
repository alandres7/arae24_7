package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class FrecuenciaLineaMetroDTO extends AbstractDTO {

	private Long idFrecuencia;
	
	private Double frecuenciaMinimaPicoAM;
	
	private Double frecuenciaMinimaPicoPM;
	
	private Double frecuenciaMinimaValleDiurno;
	
	private Double frecuenciaMinimaValleNocturno;
	
	private Double intervaloMaximoPicoAM;
	
	private Double intervaloMaximoPicoPM;
	
	private Double intervaloMaximoValleDiurno;
	
	private Double intervaloMaximoValleNocturno;
	
	private LineaMetroDTO lineaDTO;

	@Override
	public FrecuenciaLineaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public FrecuenciaLineaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}
	
	public Long getIdFrecuencia() {
		return idFrecuencia;
	}

	public FrecuenciaLineaMetroDTO setIdFrecuencia(Long idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
		return this;
	}


	public Double getFrecuenciaMinimaPicoAM() {
		return frecuenciaMinimaPicoAM;
	}

	public void setFrecuenciaMinimaPicoAM(Double frecuenciaMinimaPicoAM) {
		this.frecuenciaMinimaPicoAM = frecuenciaMinimaPicoAM;
	}

	public Double getFrecuenciaMinimaPicoPM() {
		return frecuenciaMinimaPicoPM;
	}

	public void setFrecuenciaMinimaPicoPM(Double frecuenciaMinimaPicoPM) {
		this.frecuenciaMinimaPicoPM = frecuenciaMinimaPicoPM;
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

	public Double getIntervaloMaximoPicoAM() {
		return intervaloMaximoPicoAM;
	}

	public void setIntervaloMaximoPicoAM(Double intervaloMaximoPicoAM) {
		this.intervaloMaximoPicoAM = intervaloMaximoPicoAM;
	}

	public Double getIntervaloMaximoPicoPM() {
		return intervaloMaximoPicoPM;
	}

	public void setIntervaloMaximoPicoPM(Double intervaloMaximoPicoPM) {
		this.intervaloMaximoPicoPM = intervaloMaximoPicoPM;
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

	public LineaMetroDTO getLineaDTO() {
		return lineaDTO;
	}

	public void setLineaDTO(LineaMetroDTO lineaDTO) {
		this.lineaDTO = lineaDTO;
	}

}
