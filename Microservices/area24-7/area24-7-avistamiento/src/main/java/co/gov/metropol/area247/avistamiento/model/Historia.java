package co.gov.metropol.area247.avistamiento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="T247AVI_HISTORIA", schema="AVISTAM")
public class Historia implements Serializable {

	/*
	CREATE SEQUENCE  "AVISTAM"."SEQ_HISTORIA_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
	*/
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AVISTAM.SEQ_HISTORIA_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@Size(max = 2000)
	@Column(name = "S_TITULO")
	private String titulo;
	
	@Size(max = 4000)
	@Column(name = "S_TEXTO")
	private String texto;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Column(name = "D_PUBLICACION")
	private Date fechaPublicacion;
	
	@Column(name = "N_ESTADO")
	private int estado;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@JsonIgnore
	@ManyToOne(optional = false)
    @JoinColumn(name = "ID_AVISTAMIENTO", referencedColumnName = "ID")
    private Avistamiento avistamiento;	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Avistamiento getAvistamiento() {
		return avistamiento;
	}

	public void setAvistamiento(Avistamiento avistamiento) {
		this.avistamiento = avistamiento;
	}
		
}
