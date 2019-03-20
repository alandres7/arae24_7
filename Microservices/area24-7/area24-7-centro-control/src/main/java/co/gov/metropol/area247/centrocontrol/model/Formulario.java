package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table (name = "T247CCN_FORMULARIO", schema = "CCONTROL")
public class Formulario implements Persistable<Long>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_FORMULARIO_ID", allocationSize=1)
	private Long id;
	
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "ID_APLICACION")
	private Long idAplicacion;
	
	
	@OneToMany(mappedBy="formulario")
	private List<Pregunta> preguntas;
	
	
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
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
	
	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}	

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	@Override
	public boolean isNew() {
		return (getId()==null);
	}
	
}
