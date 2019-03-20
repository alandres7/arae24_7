package co.gov.metropol.area247.huellas.entity;

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

@Entity
@Table(name="D247HUE_CONSUMO", schema="HUELLAS")
public class Consumo{
	
	@Id
	@NotNull
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_ENCUESTA_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name="ID_ENCUESTADO")
//	private Encuestado  encuestado;
	
	@ManyToOne
	@JoinColumn(name="ID_PREGUNTA")
	private PreguntaConsumo pregunta;
	
	@Column(name="F_RESPUESTA")
	private float respuesta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PreguntaConsumo getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaConsumo pregunta) {
		this.pregunta = pregunta;
	}

	public float getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(float respuesta) {
		this.respuesta = respuesta;
	}

	/**
	 * 
	 */
	public Consumo() {
		// TODO Auto-generated constructor stub
	}
	
	

}
