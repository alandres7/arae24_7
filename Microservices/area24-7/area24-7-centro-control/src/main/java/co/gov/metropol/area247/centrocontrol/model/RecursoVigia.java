package co.gov.metropol.area247.centrocontrol.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;


/*
CREATE TABLE T247CCN_RECURSO
( ID number(10) NOT NULL,
  S_NOMBRE varchar2(2000) NOT NULL,
  S_DESCRIPCION varchar2(4000)
);

ALTER TABLE T247CCN_RECURSO ADD CONSTRAINT T247CCN_RECURSO_PK PRIMARY KEY (ID);

CREATE SEQUENCE  "CCONTROL"."SEQ_RECURSO_VIGIA_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
*/


@Entity
@Table(name = "T247CCN_RECURSO", schema = "CCONTROL")
public class RecursoVigia implements Persistable<Long> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CCONTROL.SEQ_RECURSO_VIGIA_ID", sequenceName="CCONTROL.SEQ_RECURSO_VIGIA_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_RECURSO_VIGIA_ID")
	private Long id;	
	
	@NotNull
	@Column(name="S_NOMBRE")
	private String nombre;
	
	@Column(name="S_DESCRIPCION")
	private String descripcion;

	

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

}
