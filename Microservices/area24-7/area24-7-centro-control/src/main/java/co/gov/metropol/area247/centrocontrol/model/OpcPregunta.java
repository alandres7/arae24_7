package co.gov.metropol.area247.centrocontrol.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="T247CCN_OPCIONES_PREGUNTA", schema="CCONTROL")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="Pregunta")
public class OpcPregunta implements Persistable<Long>, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_OPCIONES_PREGUNTA_ID", allocationSize=1)
	private Long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "ID_PREGUNTA", referencedColumnName = "ID")
	private Pregunta preguntaId;
	
	
	@Column(name = "S_CLAVE")
	private String clave;
	
	
	@Column(name="S_VALOR")
	private String valor;
		
	@Override
	public Long getId() {
		return id;
	}


	public Pregunta getIdPregunta() {
		return preguntaId;
	}


	public void setIdPregunta(Pregunta preguntaId) {
		this.preguntaId = preguntaId;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public boolean isNew() {
		return (getId()==null);
	}
	
	public OpcPregunta() {
		//
	}
	
}
