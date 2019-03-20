package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.huellas.entity.Encuestado;
import co.gov.metropol.area247.huellas.entity.PreguntaConsumo;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ConsumoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6223526093554127612L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador del consumo", required= true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador del encuestado asociado al consumo", required= true)
	private Encuestado  encuestado;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador de la pregunta asociada al consumo", required= true)
	private PreguntaConsumo pregunta;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Respuesta para el consumo asociado", required= true)
	private float respuesta;
	
	public ConsumoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Encuestado getEncuestado() {
		return encuestado;
	}

	public void setEncuestado(Encuestado encuestado) {
		this.encuestado = encuestado;
	}

	public PreguntaConsumo getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaConsumo pregunta) {
		this.pregunta = pregunta;
	}

	public float getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(float respuesta) {
		this.respuesta = respuesta;
	}
	
	
	
}
