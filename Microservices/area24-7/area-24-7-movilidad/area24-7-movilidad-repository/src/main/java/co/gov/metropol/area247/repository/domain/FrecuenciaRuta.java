package co.gov.metropol.area247.repository.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;


@Entity
@Table(name = "T247VIA_FRECUENCIA_RUTA", schema = "MOVILIDAD")
public class FrecuenciaRuta extends Entities {

	private static final long serialVersionUID = -4088952659644217841L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "N_FUENTE_DATOS")
	private int fuenteDatos;

	@Column(name = "N_FRECUENCIA_MINIMA_PICO_AM")
	private Double frecuenciaMinimaPicoAm;

	@Column(name = "N_FRECUENCIA_MIN_VALLE_DIURNO")
	private Double frecuenciaMinimaValleDiurno;

	@Column(name = "N_FRECUENCIA_MIN_VALLE_NOCTURN")
	private Double frecuenciaMinimaValleNocturno;

	@Column(name = "N_FRECUENCIA_MINIMA_PICO_PM")
	private Double frecuenciaMinimaPicoPm;

	@Column(name = "N_INTERVALO_MAXIMO_PICO_AM")
	private Double intervaloMaximoPicoAm;

	@Column(name = "N_INTERVALO_MAX_VALLE_DIURNO")
	private Double intervaloMaximoValleDiurno;

	@Column(name = "N_INTERVALO_MAX_VALLE_NOCTURNO")
	private Double intervaloMaximoValleNocturno;

	@Column(name = "N_INTERVALO_MAXIMO_PICO_PM")
	private Double intervaloMaximoPicoPm;
	
	@Column(name = "ID_RUTA")
	private Long idRuta;

	@Override
	public FrecuenciaRuta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public FrecuenciaRuta withEnabled(boolean enabled) {
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
