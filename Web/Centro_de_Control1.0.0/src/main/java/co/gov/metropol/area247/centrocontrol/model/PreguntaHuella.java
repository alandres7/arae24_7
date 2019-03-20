package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Clase encargada de almacenar las diversas preguntas de las 
 * actividades de las encuestas del aplicativo huellas. */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreguntaHuella {

	private Long id;
	private String nombre;
	private String descripcion;
	private String orden;
	private Long idActividadHuella;
	private TipoRespuestaHuella tipoRespuestaHuella;
		
	
	public PreguntaHuella(Long id, String nombre, String descripcion, String orden, 
			              Long idActividadHuella, TipoRespuestaHuella tipoRespuestaHuella) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.orden = orden;
		this.idActividadHuella = idActividadHuella;
		this.tipoRespuestaHuella = tipoRespuestaHuella;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public Long getIdActividadHuella() {
		return idActividadHuella;
	}

	public void setIdActividadHuella(Long idActividadHuella) {
		this.idActividadHuella = idActividadHuella;
	}

	public TipoRespuestaHuella getTipoRespuestaHuella() {
		return tipoRespuestaHuella;
	}

	public void setTipoRespuestaHuella(TipoRespuestaHuella tipoRespuestaHuella) {
		this.tipoRespuestaHuella = tipoRespuestaHuella;
	}
									
}