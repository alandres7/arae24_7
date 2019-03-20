package co.gov.metropol.area247.centrocontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

/*
CREATE TABLE T247CCN_AFECTACION
( ID number(10) NOT NULL,
  S_NOMBRE varchar2(2000) NOT NULL,
  S_DESCRIPCION varchar2(4000),
  ID_RECURSO number(10),
  ID_AUTORIDAD number(10),
  CONSTRAINT "T247CCN_RECURSO_FK" FOREIGN KEY ("ID_RECURSO") REFERENCES "CCONTROL"."T247CCN_RECURSO" ("ID") ENABLE, 
  CONSTRAINT "T247CCN_AUTORIDAD_COMPETEN_FK" FOREIGN KEY ("ID_AUTORIDAD") REFERENCES "CCONTROL"."T247CCN_AUTORIDAD_COMPETENTE" ("ID") ENABLE
);

CREATE SEQUENCE  "CCONTROL"."SEQ_AFECTACION_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
*/

@Entity
@Table(name = "T247CCN_AFECTACION", schema = "CCONTROL")
public class Afectacion implements Persistable<Long> {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_AFECTACION_ID")
	@SequenceGenerator(name="CCONTROL.SEQ_AFECTACION_ID", sequenceName="CCONTROL.SEQ_AFECTACION_ID", allocationSize=1)
	private Long id;	
	
	@NotNull
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@Column(name="S_DESCRIPCION")
	private String descripcion;
	
	@OneToOne
	@JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID")
	private RecursoVigia recurso;
	
	@OneToOne
	@JoinColumn(name = "ID_AUTORIDAD", referencedColumnName = "ID")
	private AutoridadCompetente autoridad;

	

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return id==null;
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

	public RecursoVigia getRecurso() {
		return recurso;
	}

	public void setRecurso(RecursoVigia recurso) {
		this.recurso = recurso;
	}

	public AutoridadCompetente getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(AutoridadCompetente autoridad) {
		this.autoridad = autoridad;
	}
	

}
