package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.FrecuenciaRutaDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class FrecuenciaRutaResponse {
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta  en hora pico a.m", required = true)
	private Double frecuenciaMinimaPicoAm;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta en hora de normalización del tráfico diurnas.", required = true)
	private Double frecuenciaMinimaValleDiurno;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta en hora de normalización del tráfico nocturnas.", required = true)
	private Double frecuenciaMinimaValleNocturno;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora en la ruta  en hora pico p.m.", required = true)
	private Double frecuenciaMinimaPicoPm;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en hora pico a.m.", required = true)
	private Double intervaloMaximoPicoAm;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en horas no pico diurnas", required = true)
	private Double intervaloMaximoValleDiurno;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en horas no pico nocturna.", required = true)
	private Double intervaloMaximoValleNocturno;
	
	@ApiModelProperty(value = "Cantidad de vehículos que pasan por minuto por la ruta en hora pico p.m.", required = true)
	private Double intervaloMaximoPicoPm;

	public FrecuenciaRutaResponse(FrecuenciaRutaDTO frecuenciaRutaDTO) {
		this.frecuenciaMinimaPicoAm = frecuenciaRutaDTO.getFrecuenciaMinimaPicoAm();
		this.frecuenciaMinimaValleDiurno = frecuenciaRutaDTO.getFrecuenciaMinimaValleDiurno();
		this.frecuenciaMinimaValleNocturno = frecuenciaRutaDTO.getFrecuenciaMinimaValleNocturno();
		this.frecuenciaMinimaPicoPm = frecuenciaRutaDTO.getFrecuenciaMinimaPicoPm();
		this.intervaloMaximoPicoAm = frecuenciaRutaDTO.getIntervaloMaximoPicoAm();
		this.intervaloMaximoValleDiurno = frecuenciaRutaDTO.getIntervaloMaximoValleDiurno();
		this.intervaloMaximoValleNocturno = frecuenciaRutaDTO.getIntervaloMaximoValleNocturno();
		this.intervaloMaximoPicoPm = frecuenciaRutaDTO.getIntervaloMaximoPicoPm();
	}
	
	public FrecuenciaRutaResponse(FrecuenciaRutaMetroDTO frecuenciaRutaDTO) {
		this.frecuenciaMinimaPicoAm = frecuenciaRutaDTO.getFrecuenciaMinimaPicoAm();
		this.frecuenciaMinimaValleDiurno = frecuenciaRutaDTO.getFrecuenciaMinimaValleDiurno();
		this.frecuenciaMinimaValleNocturno = frecuenciaRutaDTO.getFrecuenciaMinimaValleNocturno();
		this.frecuenciaMinimaPicoPm = frecuenciaRutaDTO.getFrecuenciaMinimaPicoPm();
		this.intervaloMaximoPicoAm = frecuenciaRutaDTO.getIntervaloMaximoPicoAm();
		this.intervaloMaximoValleDiurno = frecuenciaRutaDTO.getIntervaloMaximoValleDiurno();
		this.intervaloMaximoValleNocturno = frecuenciaRutaDTO.getIntervaloMaximoValleNocturno();
		this.intervaloMaximoPicoPm = frecuenciaRutaDTO.getIntervaloMaximoPicoPm();
	}
	
	public FrecuenciaRutaResponse() {
		this.frecuenciaMinimaPicoAm = 0.0;
		this.frecuenciaMinimaValleDiurno = 0.0;
		this.frecuenciaMinimaValleNocturno = 0.0;
		this.frecuenciaMinimaPicoPm = 0.0;
		this.intervaloMaximoPicoAm = 0.0;
		this.intervaloMaximoValleDiurno = 0.0;
		this.intervaloMaximoValleNocturno = 0.0;
		this.intervaloMaximoPicoPm = 0.0;
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
	
	

}
