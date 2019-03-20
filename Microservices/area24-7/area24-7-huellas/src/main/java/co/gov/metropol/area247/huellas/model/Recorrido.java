package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Recorrido implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7476041100045720367L;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Nombre del tipo de transporte", required = true)
	private String tipoTransporte;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Kilometraje por tipo de transporte", required = true)
	private double kilometraje;
	
	
	public String getTipoTransporte() {
		return tipoTransporte;
	}
	public void setTipoTransporte(String tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}
	public double getKilometraje() {
		return kilometraje;
	}
	public void setKilometraje(double kilometraje) {
		this.kilometraje = kilometraje;
	}
	
	
	
}
