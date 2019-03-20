package co.gov.metropol.area247.seguridad.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ageofuentes
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PaisDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6137920771852173175L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificadora único en la tabla del  país", required = true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador único del  país a nivel geopolítico", required = true)
	private String nombre;
	
	@JsonProperty
	@ApiModelProperty(value = "Departamentos que pertenecen al país respectivo", required = true)
	private List<DepartamentoDto> departamentos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DepartamentoDto> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<DepartamentoDto> departamentos) {
		this.departamentos = departamentos;
	}

	public PaisDto() {}
}
