package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.contenedora.model.dto.CapaDto;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class EncuestadoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8606590338197141665L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador del  encuestado", required = true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "identificador de usuario asociado al  encuestado", required = true)
	private String username;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Flag de aceptar el reto de seguir recomendaciones para disminuir su huella")
	private boolean aceptaReto;
	
	@NotNull
	@ApiModelProperty(value = "valor que representa el tamaño de la huella del encuestado")
	private float calculoHuella;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Fecha en la que realiza la encuesta")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date fechaEncuesta;
	
	@JsonProperty
	@ApiModelProperty(value = "Tipos de huellas en las que se registra el encuestado", required = true)
	private CapaDto capa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAceptaReto() {
		return aceptaReto;
	}

	public void setAceptaReto(boolean aceptaReto) {
		this.aceptaReto = aceptaReto;
	}

	public float getCalculoHuella() {
		return calculoHuella;
	}

	public void setCalculoHuella(float calculoHuella) {
		this.calculoHuella = calculoHuella;
	}

	public Date getFechaEncuesta() {
		return fechaEncuesta;
	}

	public void setFechaEncuesta(Date fechaEncuesta) {
		this.fechaEncuesta = fechaEncuesta;
	}

	public CapaDto getCapa() {
		return capa;
	}

	public void setCapa(CapaDto capa) {
		this.capa = capa;
	}

	public EncuestadoDto() {
		// TODO Auto-generated constructor stub
	}
	
}
