package co.gov.metropol.area247.entorno.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T247ENT_RECOMENDACION_ESTACION", schema = "CONTENEDOR")
public class RecomenaireEstacion implements Serializable {

	/*
	CREATE SEQUENCE  "CONTENEDOR"."SEQ_RECOMENDACION_ESTACION_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE;
	*/
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_RECOMENDACION_ESTACION_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
		
	@Column(name = "ID_RECOMENDACION")
	private Long idRecomendacion;
	
	@Column(name = "ID_ESTACION")
	private Long idEstacion;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdRecomendacion() {
		return idRecomendacion;
	}

	public void setIdRecomendacion(Long idRecomendacion) {
		this.idRecomendacion = idRecomendacion;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}
	
}
