package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "T247CCN_PREGUNTA", schema = "CCONTROL")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="Pregunta")
public class Pregunta implements Persistable<Long>, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_PREGUNTA_ID", allocationSize=1)
	private Long id;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@ManyToOne
    @JoinColumn(name = "ID_TIPO_PREGUNTA", referencedColumnName = "ID")
	private TipoPregunta tipoPregunta;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_FORMULARIO", referencedColumnName = "ID")
	private Formulario formulario;
	
	@JsonManagedReference
	@OneToMany(mappedBy="preguntaId", fetch=FetchType.LAZY)
	private List<OpcPregunta> opcPreguntas;
	
	
	@Column(name = "N_OBLIGATORIO")
	private Boolean obligatorio;
	
	@Column(name = "N_ORDEN")
	private int orden;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return(getId()==null);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public TipoPregunta getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(TipoPregunta tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}
	
	public Boolean getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public List<OpcPregunta> getOpcPreguntas() {
		return opcPreguntas;
	}

	public void setOpcPreguntas(List<OpcPregunta> opcPreguntas) {
		this.opcPreguntas = opcPreguntas;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
}
