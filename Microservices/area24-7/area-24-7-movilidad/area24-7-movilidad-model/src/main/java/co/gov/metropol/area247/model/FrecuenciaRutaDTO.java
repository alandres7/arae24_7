package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class FrecuenciaRutaDTO extends AbstractDTO{

	private Long idItem;
	
	private int fuenteDatos;
	
	private Double frecuenciaMinimaPicoAm;
	
	private Double frecuenciaMinimaValleDiurno;
	
	private Double frecuenciaMinimaValleNocturno;
	
	private Double frecuenciaMinimaPicoPm;

	private Double intervaloMaximoPicoAm;
	
	private Double intervaloMaximoValleDiurno;
	
	private Double intervaloMaximoValleNocturno;
	
	private Double intervaloMaximoPicoPm;
	
	private Long idRuta;

	public FrecuenciaRutaDTO() {
		this.idItem = (long) 0;
		this.fuenteDatos = 0;
		this.frecuenciaMinimaPicoAm = 0.0;
		this.frecuenciaMinimaValleDiurno = 0.0;
		this.frecuenciaMinimaValleNocturno = 0.0;
		this.frecuenciaMinimaPicoPm = 0.0;
		this.intervaloMaximoPicoAm = 0.0;
		this.intervaloMaximoValleDiurno = 0.0;
		this.intervaloMaximoValleNocturno = 0.0;
		this.intervaloMaximoPicoPm = 0.0;
		this.idRuta = (long) 0;
	}

	@Override
	public FrecuenciaRutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public FrecuenciaRutaDTO withEnabled(boolean enabled) {
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

	public Double getFrecuenciaMinimaPicoAm() {
		return frecuenciaMinimaPicoAm;
	}

	public void setFrecuenciaMinimaPicoAm(Double frecuenciaMinimaPicoAm) {
		this.frecuenciaMinimaPicoAm = frecuenciaMinimaPicoAm;
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

	public Double getIntervaloMaximoPicoPm() {
		return intervaloMaximoPicoPm;
	}

	public void setIntervaloMaximoPicoPm(Double intervaloMaximoPicoPm) {
		this.intervaloMaximoPicoPm = intervaloMaximoPicoPm;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}
		
	
}
