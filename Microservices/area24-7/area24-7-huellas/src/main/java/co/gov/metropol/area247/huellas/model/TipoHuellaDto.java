package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TipoHuellaDto implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5019697104263545439L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador del  tipo de huella", required = true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "nombre del  tipo de huella", required = true)
	private String sNombre;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Descripción del  tipo de huella", required = true)
	private String sDescripcion;
	
	@JsonProperty
	@ApiModelProperty(value = "Fórmula para el tipo de huella")
	private String sFormula;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "nombre del  tipo de huella", required = true)
	private Integer duracionReto;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Unidad de medida asociada al tipo de huella", required = true)
	private UnidadMedidaDto unidadMedida;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Unidad de medida asociada al tipo de huella", required = true)
	private Set<ActividadDto> formConsumo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getsNombre() {
		return sNombre;
	}

	public void setsNombre(String sNombre) {
		this.sNombre = sNombre;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public String getsFormula() {
		return sFormula;
	}

	public void setsFormula(String sFormula) {
		this.sFormula = sFormula;
	}

	public Integer getDuracionReto() {
		return duracionReto;
	}

	public void setDuracionReto(Integer duracionReto) {
		this.duracionReto = duracionReto;
	}
	
	public UnidadMedidaDto getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedidaDto unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public TipoHuellaDto() {
		// TODO Auto-generated constructor stub
	}
	 
		
}
