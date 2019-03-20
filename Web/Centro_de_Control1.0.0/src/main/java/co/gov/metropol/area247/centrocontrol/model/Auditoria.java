package co.gov.metropol.area247.centrocontrol.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Clase encargada de almacenar las diversas recursos que se usaran en los reportes de vigias. */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auditoria {

	private Long id;
	private Long idObjeto;
	private Long idUsuario;
	private Date fecha;
    private String descripcion;
    
    
	public Auditoria() {

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
								
}