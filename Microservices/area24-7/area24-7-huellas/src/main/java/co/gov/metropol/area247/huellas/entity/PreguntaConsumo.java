package co.gov.metropol.area247.huellas.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="D247HUE_PREGUNTA", schema="HUELLAS", 
		uniqueConstraints={@UniqueConstraint( columnNames= {"S_NOMBRE"})} )
public class PreguntaConsumo{
	
	@Id
	@NotNull
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_PREGUNTA_ENCUESTA_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(unique = true, precision = 10)
	private Long id;

	//
	
	@NotNull
	@Column(name = "N_OPCIONES")
	private boolean opciones;
	
	@NotNull
	@Size(max=4000)
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@NotNull
	@Size(max=100)
	@Column(name = "S_NOMBRE", unique=true)
	private String nombre;
	
	@Column(name="TIPO_RESPUESTA")
	private boolean tipoRespuesta;
	
	@ManyToOne
	@JoinColumn(name = "ID_ACTIVIDAD")
	private Actividad actividadConsumo;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "ID_UNIDAD_MEDIDA")
	private UnidadMedida unidadMedida;
	
	@OneToMany(mappedBy="pregunta")
	private Set<Consumo> consumos = new HashSet<>();
	
	public PreguntaConsumo() {
		
	}
	
	@Override
	public String toString() {
		StringBuilder pregunta = new StringBuilder("Pregunta [id=")
				.append(id).append(", nombre=")
				.append(nombre).append(", peso=");
		return pregunta.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}
	
	@Override
	public boolean equals(Object preguntaPosible) {
		if(this == preguntaPosible) return true;
		
		if(preguntaPosible == null || getClass() != preguntaPosible.getClass())
			return false;
		
		PreguntaConsumo pregunta = (PreguntaConsumo)preguntaPosible;
		return Objects.equals(nombre, pregunta.nombre);
	}

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

	public boolean isTipoRespuesta() {
		return tipoRespuesta;
	}

	public void setTipoRespuesta(boolean tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
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
	

	public Actividad getActividadConsumo() {
		return actividadConsumo;
	}

	public void setActividadConsumo(Actividad formConsumo) {
		this.actividadConsumo = formConsumo;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Set<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(Set<Consumo> consumos) {
		this.consumos = consumos;
	}
	
	
	
}
