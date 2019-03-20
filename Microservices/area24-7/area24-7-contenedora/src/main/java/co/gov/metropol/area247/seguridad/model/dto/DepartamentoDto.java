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
public class DepartamentoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9006227215943113294L;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificadora único en la tabla del  departamento", required = true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificadora único del departamento en el país a nivel geopolítico", required = true)
	private String nombre;
	
	@JsonProperty
	@ApiModelProperty(value = "Listado de municipios asociados al departamento respectivo", required = true)
	private List<MunicipioDto> municipios;

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

	public List<MunicipioDto> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioDto> municipios) {
		this.municipios = municipios;
	}

	public DepartamentoDto() {}

}
