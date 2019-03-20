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

import com.fasterxml.jackson.annotation.JsonFormat;

/*
CREATE TABLE T247CCN_NOTIFICACION_PUSH
( ID number(10,0) NOT NULL,
  S_TITULO varchar2(4000) NOT NULL, 
  S_CUERPO varchar2(4000) NOT NULL,  
  D_FECHA timestamp NOT NULL
);

CREATE SEQUENCE  "CCONTROL"."SEQ_NOTIFICACION_PUSH_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
*/

@Entity
@Table(name = "T247CCN_NOTIFICACION_PUSH", schema = "CCONTROL")
public class NotificacionPush implements Persistable<Long> {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_NOTIFICACION_PUSH_ID")
	@SequenceGenerator(name="CCONTROL.SEQ_NOTIFICACION_PUSH_ID", sequenceName="CCONTROL.SEQ_NOTIFICACION_PUSH_ID", allocationSize=1)
	private Long id;	
	
	@NotNull
	@Column(name="S_TITULO")
	private String titulo;

	@NotNull
	@Column(name="S_CUERPO")
	private String cuerpo;
	
	@NotNull
	@Column(name="D_FECHA") 
	private Date fecha; 
	
	@Column(name="S_DESTINATARIO") 
	private String destinatario;

	
	
	public NotificacionPush() {
		
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
}
