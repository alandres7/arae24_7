package co.gov.metropol.area247.huellas.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class UnidadMedidaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4873348629979781263L;

	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Identificador de la unidad de medida", required = true)
	private Long id;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Nombre de la unidad de medida", required = true)
	private String nombreUnidad;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Nombre de la unidad básica de medida", required = true)
	private String nombreUdBasica;
	
	@NotNull
	@JsonProperty
	@ApiModelProperty(value = "Abreviatura para la unidad básica de medida", required = true)
	private String abrUnidadBasica;
	
	@JsonProperty
	@ApiModelProperty(value = "Nombre de la unidad habitual primaria de medida")
	private String nombreUdHabPrimaria;
	
	@JsonProperty
	@ApiModelProperty(value = "Abreviatura para la unidad primaria de medida")
	private String AbrUdHabPrimaria;
	
	@JsonProperty
	@ApiModelProperty(value = "Valor de conversión de la unidad básica a la habitual primaria de medida")
	private float conversionUdHabPrimaria;
	
	@JsonProperty
	@ApiModelProperty(value = "Nombre de la unidad habitual secundaria de medida")
	private String nombreUdHabSecond;
	
	@JsonProperty
	@ApiModelProperty(value = "Abreviatura para la unidad secundaria de medida")
	private String AbrUdHabSecond;
	
	@JsonProperty
	@ApiModelProperty(value = "Valor de conversión de la unidad básica a la habitual secundaria de medida")
	private float conversionUdHabSecond;
	
	@JsonProperty
	@ApiModelProperty(value = "Nombre de la unidad habitual terciaria de medida")
	private String nombreUdHabThird;
	
	@JsonProperty
	@ApiModelProperty(value = "Abreviatura para la unidad terciaria de medida")
	private String AbrUdHabThird;
	
	@JsonProperty
	@ApiModelProperty(value = "Valor de conversión de la unidad básica a la habitual terciaria de medida")
	private float conversionUdHabThird;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	public String getNombreUdBasica() {
		return nombreUdBasica;
	}

	public void setNombreUdBasica(String nombreUdBasica) {
		this.nombreUdBasica = nombreUdBasica;
	}

	public String getAbrUnidadBasica() {
		return abrUnidadBasica;
	}

	public void setAbrUnidadBasica(String abrUnidadBasica) {
		this.abrUnidadBasica = abrUnidadBasica;
	}

	public String getNombreUdHabPrimaria() {
		return nombreUdHabPrimaria;
	}

	public void setNombreUdHabPrimaria(String nombreUdHabPrimaria) {
		this.nombreUdHabPrimaria = nombreUdHabPrimaria;
	}

	public String getAbrUdHabPrimaria() {
		return AbrUdHabPrimaria;
	}

	public void setAbrUdHabPrimaria(String abrUdHabPrimaria) {
		AbrUdHabPrimaria = abrUdHabPrimaria;
	}

	public float getConversionUdHabPrimaria() {
		return conversionUdHabPrimaria;
	}

	public void setConversionUdHabPrimaria(float conversionUdHabPrimaria) {
		this.conversionUdHabPrimaria = conversionUdHabPrimaria;
	}

	public String getNombreUdHabSecond() {
		return nombreUdHabSecond;
	}

	public void setNombreUdHabSecond(String nombreUdHabSecond) {
		this.nombreUdHabSecond = nombreUdHabSecond;
	}

	public String getAbrUdHabSecond() {
		return AbrUdHabSecond;
	}

	public void setAbrUdHabSecond(String abrUdHabSecond) {
		AbrUdHabSecond = abrUdHabSecond;
	}

	public float getConversionUdHabSecond() {
		return conversionUdHabSecond;
	}

	public void setConversionUdHabSecond(float conversionUdHabSecond) {
		this.conversionUdHabSecond = conversionUdHabSecond;
	}

	public String getNombreUdHabThird() {
		return nombreUdHabThird;
	}

	public void setNombreUdHabThird(String nombreUdHabThird) {
		this.nombreUdHabThird = nombreUdHabThird;
	}

	public String getAbrUdHabThird() {
		return AbrUdHabThird;
	}

	public void setAbrUdHabThird(String abrUdHabThird) {
		AbrUdHabThird = abrUdHabThird;
	}

	public float getConversionUdHabThird() {
		return conversionUdHabThird;
	}

	public void setConversionUdHabThird(float conversionUdHabThird) {
		this.conversionUdHabThird = conversionUdHabThird;
	}

	/**
	 * 
	 */
	public UnidadMedidaDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
