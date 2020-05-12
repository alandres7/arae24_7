package co.gov.metropol.area247.covid19.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "D247CON_RECOMENDACION", schema = "CONTENEDOR")
public class Recommendation {
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_RECOMENDACION_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE", unique = true)
	private String nombre;

	@NotNull
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "S_TIPO")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name="ID_APLICACION")
	@JsonIgnore
	private App aplicacion;
	
	@Column(name = "N_ACTIVO")
	private boolean activo;
	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcionHtml(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/*public Aplicacion getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Aplicacion idAplicacion) {
		this.idAplicacion = idAplicacion;
	}*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public App getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(App aplicacion) {
		this.aplicacion = aplicacion;
	}

}
