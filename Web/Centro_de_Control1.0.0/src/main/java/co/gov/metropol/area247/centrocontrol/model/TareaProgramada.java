package co.gov.metropol.area247.centrocontrol.model;

import java.util.Date;

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
CREATE TABLE T247CCN_TAREA_PROGRAMADA
( ID number(10,0) NOT NULL,
  S_TIPO_RECURSO varchar2(20) NOT NULL,  
  ID_RECURSO number(10,0) NOT NULL,  
  D_FECHA_INI timestamp NOT NULL, 
  S_URL_RECURSO varchar2(4000) NOT NULL,  
  N_INTERVALO number(10,0) NOT NULL,
  S_UNIDAD varchar2(4000) NOT NULL,
  N_ACTIVO number(1,0) NOT NULL
);

CREATE SEQUENCE  "CCONTROL"."SEQ_TAREA_PROGRAMADA_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
*/

@Entity
@Table(name = "T247CCN_TAREA_PROGRAMADA", schema = "CCONTROL")
public class TareaProgramada implements Persistable<Long> {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_TAREA_PROGRAMADA_ID")
	@SequenceGenerator(name="CCONTROL.SEQ_TAREA_PROGRAMADA_ID", sequenceName="CCONTROL.SEQ_TAREA_PROGRAMADA_ID", allocationSize=1)
	private Long id;	
	
	@NotNull
	@Column(name="S_TIPO_RECURSO")
	private String tipoRecurso;
	
	@NotNull
	@Column(name="ID_RECURSO")
	private Long idRecurso;
	
	@NotNull
	@Column(name="D_FECHA_INI") 
	private Date fechaIni; 
	
	@NotNull
	@Column(name="S_URL_RECURSO")
	private String urlRecursoWS;
	
	@NotNull
	@Column(name="N_INTERVALO")
	private int intervalo; 
	
	@NotNull
	@Column(name="S_UNIDAD")
	private String unidad;
	
	@NotNull
	@Column(name="N_ACTIVO")
	private Boolean activo;
	
	@NotNull
	@Column(name="ID_APLICACION")
	private Long idAplicacion;
		
	
	public TareaProgramada() {
		
	}
	


	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return id==null;
	}

	public String getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(String tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getUrlRecursoWS() {
		return urlRecursoWS;
	}

	public void setUrlRecursoWS(String urlRecursoWS) {
		this.urlRecursoWS = urlRecursoWS;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	
}
