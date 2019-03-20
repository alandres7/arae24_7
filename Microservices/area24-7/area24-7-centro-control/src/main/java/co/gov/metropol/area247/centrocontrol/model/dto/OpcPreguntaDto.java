package co.gov.metropol.area247.centrocontrol.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class OpcPreguntaDto {
	
	@JsonProperty
	@ApiModelProperty(notes = "Identificador único dentro de la base de datos de la opción de pregunta")
	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Identificador único dentro de la base de datos de la pregunta asociada a la opción")
	private Long preguntaId;
	
	@JsonProperty
	@ApiModelProperty(notes = "Clave asociada a la opción")
	private String clave;
	
	@JsonProperty
	@ApiModelProperty(notes = "Valor asociado a la clave de la opción")
	private String valor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getClave() {
		return clave;
	}



	public void setClave(String clave) {
		this.clave = clave;
	}



	public String getValor() {
		return valor;
	}



	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}

	public Long getPreguntaId() {
		return preguntaId;
	}

	public OpcPreguntaDto() {
		// TODO Auto-generated constructor stub
	}

	public OpcPreguntaDto(Long id, Long preguntaId, String clave, String valor) {
		this.id = id;
		this.preguntaId = preguntaId;
		this.clave = clave;
		this.valor = valor;
	}
	
	
	
}
