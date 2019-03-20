package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class FrecuenciaRutaMetroWSDTO {

	@JsonProperty(value = "idFrecuencia")
	private String idFrecuencia;

	@JsonProperty(value = "frecuenciaMinimaPicoAM")
	private Integer frecuenciaMinimaPicoAM;

	@JsonProperty(value = "frecuenciaMinimaValleDiurno")
	private Integer frecuenciaMinimaValleDiurno;

	@JsonProperty(value = "frecuenciaMinimaValleNocturno")
	private Integer frecuenciaMinimaValleNocturno;

	@JsonProperty(value = "frecuenciaMinimaPicoPM")
	private Integer frecuenciaMinimaPicoPM;

	@JsonProperty(value = "intervaloMaximoPicoAM")
	private Integer intervaloMaximoPicoAM;

	@JsonProperty(value = "intervaloMaximoValleDiurno")
	private Integer intervaloMaximoValleDiurno;

	@JsonProperty(value = "intervaloMaximoValleNocturno")
	private Integer intervaloMaximoValleNocturno;

	@JsonProperty(value = "intervaloMaximoPicoPM")
	private Integer intervaloMaximoPicoPM;

	@JsonProperty(value = "idTipoEstadoRegistro")
	private Integer idTipoEstadoRegistro;

	@JsonProperty(value = "descEstadoRegistro")
	private Integer descEstadoRegistro;

	@JsonProperty(value = "idRuta")
	private Integer idRuta;

	public String getIdFrecuencia() {
		return idFrecuencia;
	}

	public void setIdFrecuencia(String idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
	}

	public Integer getFrecuenciaMinimaPicoAM() {
		return frecuenciaMinimaPicoAM;
	}

	public void setFrecuenciaMinimaPicoAM(Integer frecuenciaMinimaPicoAM) {
		this.frecuenciaMinimaPicoAM = frecuenciaMinimaPicoAM;
	}

	public Integer getFrecuenciaMinimaValleDiurno() {
		return frecuenciaMinimaValleDiurno;
	}

	public void setFrecuenciaMinimaValleDiurno(Integer frecuenciaMinimaValleDiurno) {
		this.frecuenciaMinimaValleDiurno = frecuenciaMinimaValleDiurno;
	}

	public Integer getFrecuenciaMinimaValleNocturno() {
		return frecuenciaMinimaValleNocturno;
	}

	public void setFrecuenciaMinimaValleNocturno(Integer frecuenciaMinimaValleNocturno) {
		this.frecuenciaMinimaValleNocturno = frecuenciaMinimaValleNocturno;
	}

	public Integer getFrecuenciaMinimaPicoPM() {
		return frecuenciaMinimaPicoPM;
	}

	public void setFrecuenciaMinimaPicoPM(Integer frecuenciaMinimaPicoPM) {
		this.frecuenciaMinimaPicoPM = frecuenciaMinimaPicoPM;
	}

	public Integer getIntervaloMaximoPicoAM() {
		return intervaloMaximoPicoAM;
	}

	public void setIntervaloMaximoPicoAM(Integer intervaloMaximoPicoAM) {
		this.intervaloMaximoPicoAM = intervaloMaximoPicoAM;
	}

	public Integer getIntervaloMaximoValleDiurno() {
		return intervaloMaximoValleDiurno;
	}

	public void setIntervaloMaximoValleDiurno(Integer intervaloMaximoValleDiurno) {
		this.intervaloMaximoValleDiurno = intervaloMaximoValleDiurno;
	}

	public Integer getIntervaloMaximoValleNocturno() {
		return intervaloMaximoValleNocturno;
	}

	public void setIntervaloMaximoValleNocturno(Integer intervaloMaximoValleNocturno) {
		this.intervaloMaximoValleNocturno = intervaloMaximoValleNocturno;
	}

	public Integer getIntervaloMaximoPicoPM() {
		return intervaloMaximoPicoPM;
	}

	public void setIntervaloMaximoPicoPM(Integer intervaloMaximoPicoPM) {
		this.intervaloMaximoPicoPM = intervaloMaximoPicoPM;
	}

	public FrecuenciaRutaMetroDTO getFrecuenciaRutaDTO() {
		FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO = new FrecuenciaRutaMetroDTO();
		frecuenciaRutaMetroDTO.setFrecuenciaMinimaPicoAm(getFrecuenciaMinimaPicoAM().doubleValue());
		frecuenciaRutaMetroDTO.setFrecuenciaMinimaValleDiurno(getFrecuenciaMinimaValleDiurno().doubleValue());
		frecuenciaRutaMetroDTO.setFrecuenciaMinimaValleNocturno(getFrecuenciaMinimaValleNocturno().doubleValue());
		frecuenciaRutaMetroDTO.setFrecuenciaMinimaPicoPm(getFrecuenciaMinimaPicoPM().doubleValue());
		frecuenciaRutaMetroDTO.setIntervaloMaximoPicoAm(getIntervaloMaximoPicoAM().doubleValue());
		frecuenciaRutaMetroDTO.setIntervaloMaximoValleDiurno(getIntervaloMaximoValleDiurno().doubleValue());
		frecuenciaRutaMetroDTO.setIntervaloMaximoValleNocturno(getIntervaloMaximoValleNocturno().doubleValue());
		frecuenciaRutaMetroDTO.setIntervaloMaximoPicoPm(getIntervaloMaximoPicoPM().doubleValue());

		return frecuenciaRutaMetroDTO;
	}
}
