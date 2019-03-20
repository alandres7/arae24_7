package co.gov.metropol.area247.centrocontrol.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Avistamiento {

	private Long id;
	private Long idUsuario;
	private String username;
	private String nombreComun;		
	private String descripcion;		
	private String nombreCientifico;			
	private String estadoPublicacion;
	private String nombreEstado;
	private String colorEstado;
	private String activo;
	private Long idIcono;	
	private Long idMarcador;
	private Long idCapa;
	private Long idCategoria;
	private Long idEspecie;
	private String idMultimedia;
	private String rutaMultimedia;
	private String rutaIcono;
	private String tipoAvistamiento;
	private String tipoEspecie;
	private boolean tieneHistoria;
	private String fechaCreacion;
	private Integer totalRecords;
	private String botonVisualizar;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public String getNombreComun() {
		return nombreComun;
	}
	
	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getNombreCientifico() {
		return nombreCientifico;
	}
	
	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}
	
	public String getEstadoPublicacion() {
		return estadoPublicacion;
	}
	
	public void setEstadoPublicacion(String estadoPublicacion) {
		this.estadoPublicacion = estadoPublicacion;
	}
		
	public String getNombreEstado() {
		return nombreEstado;
	}
	
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
	public String getColorEstado() {
		return colorEstado;
	}
	
	public void setColorEstado(String colorEstado) {
		this.colorEstado = colorEstado;
	}
	
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	public Long getIdIcono() {
		return idIcono;
	}
	
	public void setIdIcono(Long idIcono) {
		this.idIcono = idIcono;
	}
			
	public Long getIdMarcador() {
		return idMarcador;
	}
	
	public void setIdMarcador(Long idMarcador) {
		this.idMarcador = idMarcador;
	}
		
	public Long getIdCapa() {
		return idCapa;
	}
	
	public void setIdCapa(Long idCapa) {
		this.idCapa = idCapa;
	}
		
	public Long getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
		
	public Long getIdEspecie() {
		return idEspecie;
	}
	
	public void setIdEspecie(Long idEspecie) {
		this.idEspecie = idEspecie;
	}
	
	public String getIdMultimedia() {
		return idMultimedia;
	}
	
	public void setIdMultimedia(String idMultimedia) {
		this.idMultimedia = idMultimedia;
	}
	
	public String getRutaMultimedia() {
		return rutaMultimedia;
	}
	
	public void setRutaMultimedia(String rutaMultimedia) {
		this.rutaMultimedia = rutaMultimedia;
	}
			
	public String getRutaIcono() {
		return rutaIcono;
	}
	
	public void setRutaIcono(String rutaIcono) {
		this.rutaIcono = rutaIcono;
	}
	
	public String getTipoAvistamiento() {
		return tipoAvistamiento;
	}
	
	public void setTipoAvistamiento(String tipoAvistamiento) {
		this.tipoAvistamiento = tipoAvistamiento;
	}
	
	public String getTipoEspecie() {
		return tipoEspecie;
	}
	
	public void setTipoEspecie(String tipoEspecie) {
		this.tipoEspecie = tipoEspecie;
	}
			
	public boolean isTieneHistoria() {
		return tieneHistoria;
	}
	
	public void setTieneHistoria(boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}
	
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Integer getTotalRecords() {
		return totalRecords;
	}
	
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public String getBotonVisualizar() {
		return botonVisualizar;
	}
	
	public void setBotonVisualizar(String botonVisualizar) {
		this.botonVisualizar = botonVisualizar;
	}
	
}