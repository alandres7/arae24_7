package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.huellas.entity.UnidadMedida;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PreguntaConsumoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3269758810631010899L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador de la pregunta", required= true)
	private Long id;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Determina si la respuesta a la pregunta se basa en opciones", required= true)
	private boolean opciones;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Valor mínimo para la unidad de medida asociada a la pregunta", required= true)
	private String descripcion;
	
	@JsonProperty
	@ApiModelProperty(value = "Flag para determinar si hay pregunta tipo-tiempo asociada a la pregunta principal")
	private boolean tipoTiempo;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Clave única asociada a la pregunta", required= true)
	private String nombre;
	
//	@NotNull
//	@JsonProperty
//	@ApiModelProperty(value = "El tipo de huella asociado a la pregunta", required= true)
//	private Encuesta tipoHuella;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "La unidad de medida asociada a la pregunta", required= true)
	private UnidadMedida unidadMedida;
	
	
	
	public PreguntaConsumoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isOpciones() {
		return opciones;
	}

	public void setOpciones(boolean opciones) {
		this.opciones = opciones;
	}

	public boolean isTipoTiempo() {
		return tipoTiempo;
	}

	public void setTipoTiempo(boolean tipoTiempo) {
		this.tipoTiempo = tipoTiempo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public PreguntaConsumoDto(Long id, String descripcion, String nombre,
			Long idUnidadMedida, String abrUnidadBasica) {
		this.id = id;
		this.descripcion = descripcion;
		//Pendiente llamada al formulario asociado
		this.setUnidadMedida(new UnidadMedida());
		this.unidadMedida.setId(idUnidadMedida);
		this.unidadMedida.setAbrUnidadBasica(abrUnidadBasica);
	}
	
	
	
	
}
