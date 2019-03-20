package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteVigia {

	private Long id;
	private String fechaReporte;
	private String descripcion;
	private String direccion;
	private String estado;
	private String nombreEstado;
	private int activo;
	private String radicadoSim;
	private Long idUsuario;
	private String username;
	private Long idNodoArbol;
	private String recorridoArbol;	
	
	private Long idMarcador;
	private Long idIcono;
	private String rutaIcono;
	private Long idMultimedia;
	private String rutaMultimedia;
	private String latitud; 
	private String longitud;
	private String botonVisualizar;
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFechaReporte() {
		return fechaReporte;
	}
	
	public void setFechaReporte(String fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
		
	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public int getActivo() {
		return activo;
	}
	
	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public String getRadicadoSim() {
		return radicadoSim;
	}
	
	public void setRadicadoSim(String radicadoSim) {
		this.radicadoSim = radicadoSim;
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
	
	public Long getIdNodoArbol() {
		return idNodoArbol;
	}
	
	public void setIdNodoArbol(Long idNodoArbol) {
		this.idNodoArbol = idNodoArbol;
	}
	
	public String getRecorridoArbol() {
		return recorridoArbol;
	}
	
	public void setRecorridoArbol(String recorridoArbol) {
		this.recorridoArbol = recorridoArbol;
	}
	
	public Long getIdMarcador() {
		return idMarcador;
	}
	
	public void setIdMarcador(Long idMarcador) {
		this.idMarcador = idMarcador;
	}
	
	public Long getIdIcono() {
		return idIcono;
	}
	
	public void setIdIcono(Long idIcono) {
		this.idIcono = idIcono;
	}
	
	public String getRutaIcono() {
		return rutaIcono;
	}
	
	public void setRutaIcono(String rutaIcono) {
		this.rutaIcono = rutaIcono;
	}
	
	public Long getIdMultimedia() {
		return idMultimedia;
	}
	
	public void setIdMultimedia(Long idMultimedia) {
		this.idMultimedia = idMultimedia;
	}
	
	public String getRutaMultimedia() {
		return rutaMultimedia;
	}
	
	public void setRutaMultimedia(String rutaMultimedia) {
		this.rutaMultimedia = rutaMultimedia;
	}
	
	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getBotonVisualizar() {
		return botonVisualizar;
	}

	public void setBotonVisualizar(String botonVisualizar) {
		this.botonVisualizar = botonVisualizar;
	}
			
}