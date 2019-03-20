package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "D247CON_ICONO", schema = "CONTENEDOR")
public class Icono implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	  @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_ICONO_ID", allocationSize=1)
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	  private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Ruta públcia donde se encuentra el ícono", required = true)
	@Column(name = "S_RUTA_LOGO", unique = true)
	private String rutaLogo;
	
	@JsonProperty
	@Size(max = 100)
	@Column(name = "S_NOMBRE", unique = true)
	private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRutaLogo() {
		return rutaLogo;
	}

	public void setRutaLogo(String rutaLogo) {
		this.rutaLogo = rutaLogo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
