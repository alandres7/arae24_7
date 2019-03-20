package co.gov.metropol.area247.centrocontrol.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComentarioVigia {

	
	private Long id;
	private String textoComentario;
	@DateTimeFormat(pattern = "dd-MMM-YYYY")
	private Date fechaComentario;
	private int estado;
	private Long idUsuario;
	private String recorridoArbol;
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTextoComentario() {
		return textoComentario;
	}
	
	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}
	
	public Date getFechaComentario() {
		return fechaComentario;
	}
	
	public void setFechaComentario(Date fechaComentario) {
		this.fechaComentario = fechaComentario;
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
	
	public String getRecorridoArbol() {
		return recorridoArbol;
	}
	
	public void setRecorridoArbol(String recorridoArbol) {
		this.recorridoArbol = recorridoArbol;
	}
	

}
	