package co.gov.metropol.area247.repository.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;


@Entity
@Table(name = "T247VIA_FRECUENCIA_LINEA", schema = "MOVILIDAD")
public class FrecuenciaLinea extends Entities {
	
	private static final long serialVersionUID = -4088952659644217841L;

	@Column(name = "N_ID_ITEM")
	private Long idFrecuencia;

	@Column(name = "N_FRECUENCIA_MINIMA_PICO_AM")
	private Double frecuenciaMinimaPicoAM;
	
	@Column(name = "N_FRECUENCIA_MIN_VALLE_DIURNO")
	private Double frecuenciaMinimaValleDiurno;
	
	@Column(name = "N_FRECUENCIA_MIN_VALLE_NOCTURN")
	private Double frecuenciaMinimaValleNocturno;
	
	@Column(name = "N_FRECUENCIA_MINIMA_PICO_PM")
	private Double frecuenciaMinimaPicoPM;
		
	@Column(name = "N_INTERVALO_MAXIMO_PICO_AM")
	private Double intervaloMaximoPicoAM;
	
	@Column(name = "N_INTERVALO_MAX_VALLE_DIURNO")
	private Double intervaloMaximoValleDiurno;
	
	@Column(name = "N_INTERVALO_MAX_VALLE_NOCTURNO")
	private Double intervaloMaximoValleNocturno;
	
	@Column(name = "N_INTERVALO_MAXIMO_PICO_PM")
	private Double intervaloMaximoPicoPM;
	
//	@ManyToOne
//	@JoinColumn(name = "ID_LINEA", nullable = false)
//	private LineaMetro linea;
	
	@Column(name = "ID_LINEA")
	private Long idLinea;
	
	
	@Override
	public FrecuenciaLinea withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public FrecuenciaLinea withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}
	
	public Long getIdFrecuencia() {
		return idFrecuencia;
	}

	public FrecuenciaLinea setIdFrecuencia(Long idFrecuencia) {
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

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}
	
	

//	public LineaMetro getLinea() {
//		return linea;
//	}
//
//	public void setLinea(LineaMetro linea) {
//		this.linea = linea;
//	}
}
