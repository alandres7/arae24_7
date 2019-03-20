package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class FrecuenciaLineaResponse {

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta  en hora pico a.m.", required = true)
	private Double frecuenciaMinimaPicoAM;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta en hora de normalización del tráfico diurnas", required = true)
	private Double frecuenciaMinimaValleDiurno;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta en hora de normalización del tráfico nocturnas", required = true)
	private Double frecuenciaMinimaValleNocturno;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta  en hora pico p.m.", required = true)
	private Double frecuenciaMinimaPicoPM;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en hora pico a.m.", required = true)
	private Double intervaloMaximoPicoAM;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en horas no pico diurnas", required = true)
	private Double intervaloMaximoValleDiurno;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en horas no pico nocturna", required = true)
	private Double intervaloMaximoValleNocturno;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en hora pico p.m.", required = true)
	private Double intervaloMaximoPicoPM;

	public FrecuenciaLineaResponse(FrecuenciaLineaMetroDTO frecuencia) {
		this.frecuenciaMinimaPicoAM = frecuencia.getFrecuenciaMinimaPicoAM();
		this.frecuenciaMinimaValleDiurno = frecuencia.getFrecuenciaMinimaValleDiurno();
		this.frecuenciaMinimaValleNocturno = frecuencia.getFrecuenciaMinimaValleNocturno();
		this.frecuenciaMinimaPicoPM = frecuencia.getFrecuenciaMinimaPicoPM();
		this.intervaloMaximoPicoAM = frecuencia.getIntervaloMaximoPicoAM();
		this.intervaloMaximoValleDiurno = frecuencia.getIntervaloMaximoValleDiurno();
		this.intervaloMaximoValleNocturno = frecuencia.getIntervaloMaximoValleNocturno();
		this.intervaloMaximoPicoPM = frecuencia.getIntervaloMaximoPicoPM();
	}
	
	public FrecuenciaLineaResponse () {
		
	}

	public Double getFrecuenciaMinimaPicoAM() {
		return frecuenciaMinimaPicoAM;
	}

	public void setFrecuenciaMinimaPicoAM(Double frecuenciaMinimaPicoAM) {
		this.frecuenciaMinimaPicoAM = frecuenciaMinimaPicoAM;
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

	public Double getFrecuenciaMinimaPicoPM() {
		return frecuenciaMinimaPicoPM;
	}

	public void setFrecuenciaMinimaPicoPM(Double frecuenciaMinimaPicoPM) {
		this.frecuenciaMinimaPicoPM = frecuenciaMinimaPicoPM;
	}

	public Double getIntervaloMaximoPicoAM() {
		return intervaloMaximoPicoAM;
	}

	public void setIntervaloMaximoPicoAM(Double intervaloMaximoPicoAM) {
		this.intervaloMaximoPicoAM = intervaloMaximoPicoAM;
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

	public Double getIntervaloMaximoPicoPM() {
		return intervaloMaximoPicoPM;
	}

	public void setIntervaloMaximoPicoPM(Double intervaloMaximoPicoPM) {
		this.intervaloMaximoPicoPM = intervaloMaximoPicoPM;
	}

}
