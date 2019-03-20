package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ComentarioHisto{

	private Long  id;    
    private String descripcion;        
    private int estadoPublicacion;
    private String colorEstado;
    private String nombreEstado;
    private Long idUsuario;
    private String username;
	private String fechaCreacion;	
    
       
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
	
	public int getEstadoPublicacion() {
		return estadoPublicacion;
	}

	public void setEstadoPublicacion(int estadoPublicacion) {
		this.estadoPublicacion = estadoPublicacion;
	}
		
	public String getColorEstado() {
		return colorEstado;
	}

	public void setColorEstado(String colorEstado) {
		this.colorEstado = colorEstado;
	}
		
	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
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

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}