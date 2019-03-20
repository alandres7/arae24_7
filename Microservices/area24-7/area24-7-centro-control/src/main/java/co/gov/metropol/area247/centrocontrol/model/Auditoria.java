package co.gov.metropol.area247.centrocontrol.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.data.domain.Persistable;
import com.fasterxml.jackson.annotation.JsonFormat;

//CREATE SEQUENCE  "CCONTROL"."SEQ_AUDITORIA_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

@Entity
@Table(name = "T247AUD_AUDITORIA", schema = "CCONTROL")
public class Auditoria implements Persistable<Long>{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_AUDITORIA_ID")
	@SequenceGenerator(name="CCONTROL.SEQ_AUDITORIA_ID", sequenceName="CCONTROL.SEQ_AUDITORIA_ID", allocationSize=1)
	private Long id;
        
	@Column(name = "ID_OBJETO")
	private Long idObjeto;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@Column(name = "D_FECHA")
	private Date fecha;
	
    @Column(name = "S_DESCRIPCION")
    private String descripcion;
    

	public Auditoria() {
		super();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public boolean isNew() {
		return (getId()==null);
	}
  
}