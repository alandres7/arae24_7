package co.gov.metropol.area247.huellas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <h1>Unidad de Medida!</h1>
 * */
@Entity
@Table(name="T247HUE_UNIDAD_MEDIDA", schema="HUELLAS")
public class UnidadMedida{
	
	@Id
	@NotNull
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_UNIDAD_MEDIDA_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(unique=true, precision=10)
	private Long id;
	
	@NotNull
	@Column(name="S_NOMBRE")
	private String nombreUnidad;
	
	@NotNull
	@Column(name="S_UNIDAD_BASICA")
	private String nombreUdBasica;
	
	@NotNull
	@Column(name="S_ABREVIATURA_UB")
	private String abrUnidadBasica;
	
	@Column(name="S_UNIDAD_HABITUAL_PRIMARIA")
	private String nombreUdHabPrimaria;
	
	@Column(name="S_ABREVIATURA_HP")
	private String AbrUdHabPrimaria;
	
	@Column(name="F_CONVERSION_HP")
	private float conversionUdHabPrimaria;
	
	@Column(name="S_UNIDAD_HABITUAL_SECOND")
	private String nombreUdHabSecond;
	
	@Column(name="S_ABREVIATURA_HS")
	private String AbrUdHabSecond;
	
	@Column(name="F_CONVERSION_HS")
	private float conversionUdHabSecond;
	
	@Column(name="S_UNIDAD_HABITUAL_THIRD")
	private String nombreUdHabThird;
	
	@Column(name="S_ABREVIATURA_HT")
	private String AbrUdHabThird;
	
	@Column(name="F_CONVERSION_HT")
	private float conversionUdHabThird;
	
	public UnidadMedida() {}

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
	
	
	
}
