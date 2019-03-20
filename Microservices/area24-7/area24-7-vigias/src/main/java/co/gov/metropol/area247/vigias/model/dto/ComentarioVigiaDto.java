package co.gov.metropol.area247.vigias.model.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ComentarioVigiaDto {
	
	private Long id;
	private String descripcion;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date fechaCreacion;
	private String estado;
	private Long idUsuario;
	private String username;
    private Long idReporteVigia;
	private String recorridoArbol;
	
	
	
	public ComentarioVigiaDto(Long id, String descripcion, Date fechaCreacion, String estado, Long idUsuario,
			Long idReporteVigia, String recorridoArbol) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.idUsuario = idUsuario;
		this.idReporteVigia = idReporteVigia;
		this.recorridoArbol = recorridoArbol;
	}


	public ComentarioVigiaDto() {
	
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
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
