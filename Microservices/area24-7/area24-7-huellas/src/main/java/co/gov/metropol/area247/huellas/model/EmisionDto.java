package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class EmisionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8953794028294291257L;
	
	@JsonProperty
	@ApiModelProperty(value = "Emisión de CO2 en la ruta determinada")
	private double emisionCO2;
	
	@JsonProperty
	@ApiModelProperty(value = "Emisión de PM2.5 en la ruta determinada")
	private double emisionPM2_5;
	
	
	@JsonProperty
	@ApiModelProperty(value = "Emisión de CO2 autos en la ruta determinada para comparativo con la emisión asociada al transporte público")
	private double emisionCO2Autos;

	/**
	 * @return the emisionCO2
	 */
	public double getEmisionCO2() {
		return emisionCO2;
	}
	/**
	 * @param emisionCO2 the emisionCO2 to set
	 */
	public void setEmisionCO2(double emisionCO2) {
		this.emisionCO2 = emisionCO2;
	}	
	
	/**
	 * @return the emisionPM2_5
	 */
	public double getEmisionPM2_5() {
		return emisionPM2_5;
	}
	/**
	 * @param emisionPM2_5 the emisionPM2_5 to set
	 */
	public void setEmisionPM2_5(double emisionPM2_5) {
		this.emisionPM2_5 = emisionPM2_5;
	}
	
	public double getEmisionCO2Autos() {
		return emisionCO2Autos;
	}
	public void setEmisionCO2Autos(double emisionCO2Autos) {
		this.emisionCO2Autos = emisionCO2Autos;
	}
	public EmisionDto() {
		
	}
	
	@Override
	public String toString() {
		return "Emision(es)";
	}

}
