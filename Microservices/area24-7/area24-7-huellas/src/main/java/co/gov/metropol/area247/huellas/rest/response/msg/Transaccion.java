package co.gov.metropol.area247.huellas.rest.response.msg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Transaccion implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6533372942698119081L;

	@JsonProperty
	@ApiModelProperty(value="Código de la transacción realizada")
	private Long code;
	
	@JsonProperty
	@ApiModelProperty(value="Estado de la transacción realizada")
	private String status;
	
	@JsonProperty
	@ApiModelProperty(value="Descripción de la transacción realizada")
	private String descripcion;
	
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Transaccion() {}
	
	
}
