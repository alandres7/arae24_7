package co.gov.metropol.area247.seguridad.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ageofuentes
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class MunicipioDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9023010136865825730L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificadora único en la tabla del municipio", required = true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "nombre del municipio respectivo", required = true)
	private String nombre;
		
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
	
	public MunicipioDto() {}

	public MunicipioDto(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
}
