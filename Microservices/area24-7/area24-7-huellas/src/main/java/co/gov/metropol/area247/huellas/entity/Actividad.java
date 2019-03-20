package co.gov.metropol.area247.huellas.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.contenedora.model.Capa;

@Entity
@Table(name="D247HUE_ACTIVIDAD", schema="HUELLAS")
public class Actividad {
	
	@Id
	@NotNull
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_ACTIVIDAD_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@Column(unique=true, precision=10)
	private long id;
	
	@Size(max=200)
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@Size(max=2000)
	@Column(name="S_DESCRIPCION")
	private String descripcion;
	
	@Column(name="N_DECISION")
	private boolean decision;
	
	@Column(name="N_GLOBAL")
	private boolean global;
	
	@Column(name="N_ORDEN")
	private int orden;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ENCUESTA")
	private Capa capa;
		
	@OneToMany(mappedBy = "actividadConsumo", cascade = CascadeType.ALL)
	private Set<PreguntaConsumo> preguntasEncuesta =  new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isDecision() {
		return decision;
	}

	public void setDecision(boolean decision) {
		this.decision = decision;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public Capa getCapa() {
		return capa;
	}

	public void setCapa(Capa capa) {
		this.capa = capa;
	}

	public Set<PreguntaConsumo> getPreguntasEncuesta() {
		return preguntasEncuesta;
	}

	public void setPreguntasEncuesta(Set<PreguntaConsumo> preguntasEncuesta) {
		this.preguntasEncuesta = preguntasEncuesta;
	}	
	
}
