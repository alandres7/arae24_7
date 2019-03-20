package co.gov.metropol.area247.services.rest.gtpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.FrecuenciaRutaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class FrecuenciaGtpcWSDTO {

	@JsonProperty(value = "idFrecuencia")
	private Long idFrecuencia;
	
	@JsonProperty(value = "frecuenciaMinimaPicoAm")
	private Double frecuenciaMinimaPicoAm;
	
	@JsonProperty(value = "frecuenciaMinimaValleDiurno")
	private Double frecuenciaMinimaValleDiurno;
	
	@JsonProperty(value = "frecuenciaMinimaValleNocturno")
	private Double frecuenciaMinimaValleNocturno;
	
	@JsonProperty(value = "frecuenciaMinimaPicoPm")
	private Double frecuenciaMinimaPicoPm;
	
	@JsonProperty(value = "intervaloMaximoPicoAm")
	private Double intervaloMaximoPicoAm;
	
	@JsonProperty(value = "intervaloMaximoValleDiurno")
	private Double intervaloMaximoValleDiurno;
	
	@JsonProperty(value = "intervaloMaximoValleNocturno")
	private Double intervaloMaximoValleNocturno;
	
	@JsonProperty(value = "intervaloMaximoPicoPm")
	private Double intervaloMaximoPicoPm;
	
	@JsonProperty(value = "idTipoEstadoRegistro")
	private Long idTipoEstadoRegistro;
	
	@JsonProperty(value = "descEstadoRegistro")
	private String descEstadoRegistro;
	
	@JsonProperty(value = "idruta")
	private Long idRuta;

	public Long getIdFrecuencia() {
		return idFrecuencia;
	}

	public void setIdFrecuencia(Long idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
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
	
	public String getDescEstadoRegistro() {
		return descEstadoRegistro;
	}

	public void setDescEstadoRegistro(String descEstadoRegistro) {
		this.descEstadoRegistro = descEstadoRegistro;
	}

	public Long getIdTipoEstadoRegistro() {
		return idTipoEstadoRegistro;
	}

	public void setIdTipoEstadoRegistro(Long idTipoEstadoRegistro) {
		this.idTipoEstadoRegistro = idTipoEstadoRegistro;
	}

	public FrecuenciaRutaDTO getFrecuenciaRuta() {
		FrecuenciaRutaDTO frecuenciaRuta = new FrecuenciaRutaDTO();
		frecuenciaRuta.setIdItem(this.idFrecuencia);
		frecuenciaRuta.setFuenteDatos(2);
		frecuenciaRuta.setFrecuenciaMinimaPicoAm(this.frecuenciaMinimaPicoAm);
		frecuenciaRuta.setFrecuenciaMinimaValleDiurno(this.frecuenciaMinimaValleDiurno);
		frecuenciaRuta.setFrecuenciaMinimaValleNocturno(this.frecuenciaMinimaValleNocturno);
		frecuenciaRuta.setFrecuenciaMinimaPicoPm(this.frecuenciaMinimaPicoPm);
		frecuenciaRuta.setIntervaloMaximoPicoAm(this.intervaloMaximoPicoAm);
		frecuenciaRuta.setIntervaloMaximoValleDiurno(this.intervaloMaximoValleDiurno);
		frecuenciaRuta.setIntervaloMaximoValleNocturno(this.intervaloMaximoValleNocturno);
		frecuenciaRuta.setIntervaloMaximoPicoPm(this.intervaloMaximoPicoPm);
		frecuenciaRuta.setIdRuta(this.idRuta);
		return frecuenciaRuta;
	}
	
}
