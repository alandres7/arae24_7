package co.gov.metropol.area247.avistamiento.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ComentarioHistoriaDto{

	private Long id;
	private String descripcion;	
	private int estadoPublicacion;
	private Long idUsuario;	
	private String username;
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date fechaCreacion;
	
		
	public ComentarioHistoriaDto(Long id, String descripcion, Date fechaCreacion, int estadoPublicacion, Long idUsuario) {
		this.id = id;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.estadoPublicacion = estadoPublicacion;
		this.idUsuario = idUsuario;
	}

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
		
	public int getEstadoPublicacion() {
		return estadoPublicacion;
	}

	public void setEstadoPublicacion(int estadoPublicacion) {
		this.estadoPublicacion = estadoPublicacion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
}
