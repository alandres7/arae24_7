package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar los diversos mensajes de advertencia o información que se usaran en los aplicativos del Area 24/7 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Mensaje {
	
    private Long id;
	private String contenido;
	private String titulo;
	private String nombreIdentificador;
	private String uso;
	private Long idAplicacion;
	private String nombreAplicacion;
    
    
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreIdentificador() {
		return nombreIdentificador;
	}

	public void setNombreIdentificador(String nombreIdentificador) {
		this.nombreIdentificador = nombreIdentificador;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getNombreAplicacion() {
		return nombreAplicacion;
	}

	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}
			
}