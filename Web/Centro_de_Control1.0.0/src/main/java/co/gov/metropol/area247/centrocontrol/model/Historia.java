package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Clase encargada de almacenar las diversos historias correspondientes a los avistamientos */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Historia implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long  id;   
	private String titulo;
	private String texto;
	private int estadoPublicacion;
	private String colorEstado;
	private String nombreEstado;
	private Long idUsuario;
	private String username;
	private String fechaCreacion;
	private int cantidadComenPen;
	private int cantidadComenTotal;

	
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
