package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class FrecuenciaLineaMetroWSDTO {

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

	public FrecuenciaLineaMetroDTO getFrecuenciaLineaDTO() {
		FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO = new FrecuenciaLineaMetroDTO();
		frecuenciaLineaMetroDTO.setFrecuenciaMinimaPicoAM(getFrecuenciaMinimaPicoAM().doubleValue());
		frecuenciaLineaMetroDTO.setFrecuenciaMinimaValleDiurno(getFrecuenciaMinimaValleDiurno().doubleValue());
		frecuenciaLineaMetroDTO.setFrecuenciaMinimaValleNocturno(getFrecuenciaMinimaValleNocturno().doubleValue());
		frecuenciaLineaMetroDTO.setFrecuenciaMinimaPicoPM(getFrecuenciaMinimaPicoPM().doubleValue());
		frecuenciaLineaMetroDTO.setIntervaloMaximoPicoAM(getIntervaloMaximoPicoAM().doubleValue());
		frecuenciaLineaMetroDTO.setIntervaloMaximoValleDiurno(getIntervaloMaximoValleDiurno().doubleValue());
		frecuenciaLineaMetroDTO.setIntervaloMaximoValleNocturno(getIntervaloMaximoValleNocturno().doubleValue());
		frecuenciaLineaMetroDTO.setIntervaloMaximoPicoPM(getIntervaloMaximoPicoPM().doubleValue());

		return frecuenciaLineaMetroDTO;
	}
}
