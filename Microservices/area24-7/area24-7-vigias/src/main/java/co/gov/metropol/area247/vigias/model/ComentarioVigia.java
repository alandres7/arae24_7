package co.gov.metropol.area247.vigias.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="T247VIG_COMENTARIOS_REPORTE", schema="VIGIAS")
public class ComentarioVigia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_COMENTARIOS_REPORTE_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@Size(max = 4000)
	@Column(name = "S_TEXTO_COMENTARIO")
	private String descripcion;
	
	@Column(name = "D_FECHA_COMENTARIO")
	private Date fechaCreacion;
	
	@Column(name = "S_ESTADO")
	private String estado;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "ID_REPORTE")
    private Long idReporteVigia;
	
	@Column(name = "S_RECORRIDO_ARBOL")
	private String recorridoArbol;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public Long getIdReporteVigia() {
		return idReporteVigia;
	}

	public void setIdReporteVigia(Long idReporteVigia) {
		this.idReporteVigia = idReporteVigia;
	}

	public String getRecorridoArbol() {
		return recorridoArbol;
	}

	public void setRecorridoArbol(String recorridoArbol) {
		this.recorridoArbol = recorridoArbol;
	}
	

}
	