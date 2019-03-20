package co.gov.metropol.area247.avistamiento.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class HistoriaDto{
 
	private Long id;
	private String titulo;
	private String texto;	
	private int estadoPublicacion;
	private Long idUsuario;	
	private String username;
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date fechaCreacion;
	private int cantidadComenPen;
	private int cantidadComenTotal;
	
		
	public HistoriaDto(Long id, String titulo, String texto, Date fechaCreacion, int estadoPublicacion, 
			           Long idUsuario, int cantidadComenPen, int cantidadComenTotal) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
		this.fechaCreacion = fechaCreacion;
		this.estadoPublicacion = estadoPublicacion;
		this.idUsuario = idUsuario;
		this.cantidadComenPen = cantidadComenPen;
		this.cantidadComenTotal = cantidadComenTotal;
	}
	
	public HistoriaDto() {
	}

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

	public int getCantidadComenPen() {
		return cantidadComenPen;
	}

	public void setCantidadComenPen(int cantidadComenPen) {
		this.cantidadComenPen = cantidadComenPen;
	}

	public int getCantidadComenTotal() {
		return cantidadComenTotal;
	}

	public void setCantidadComenTotal(int cantidadComenTotal) {
		this.cantidadComenTotal = cantidadComenTotal;
	}
						
}
