package co.gov.metropol.area247.avistamiento.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class AvistamientoDto {
	
	private Long id;
	private Long idUsuario;
	private String username;
	private Long idIcono;
	private Long idMultimedia;
	private Long idCapa;
	private Long idCategoria;
	private Long idMarcador;
	private String rutaMultimedia;
	private String nombreComun;
	private String descripcion;
	private String nombreCientifico;
	private String tipoAvistamiento;
	private String tipoEspecie;
	private int estadoPublicacion;
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date fechaCreacion;
	private Boolean tieneHistoria;
	private Long idEspecie;
	private String rutaIcono;
	
	public AvistamientoDto(Long id, Long idUsuario, Long idIcono, Long idMultimedia, Long idCapa, Long idCategoria, 
			               Long idMarcador, String nombreComun, String descripcion, String nombreCientifico, 
			               String tipoAvistamiento, String tipoEspecie, int estadoPublicacion, 
			               Date fechaCreacion, Boolean tieneHistoria, Long idEspecie) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idIcono = idIcono;
		this.idMultimedia = idMultimedia;
		this.idCapa = idCapa;
		this.idCategoria = idCategoria;
		this.idMarcador = idMarcador;
		this.nombreComun = nombreComun;
		this.descripcion = descripcion;
		this.nombreCientifico = nombreCientifico;
		this.tipoAvistamiento = tipoAvistamiento;
		this.tipoEspecie = tipoEspecie;
		this.estadoPublicacion = estadoPublicacion;
 		this.fechaCreacion = fechaCreacion;
 		this.tieneHistoria = tieneHistoria;
 		this.idEspecie = idEspecie;
	}
	
	public AvistamientoDto() {

    }



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

	public Long getIdIcono() {
		return idIcono;
	}

	public void setIdIcono(Long idIcono) {
		this.idIcono = idIcono;
	}
		
	public Long getIdMultimedia() {
		return idMultimedia;
	}

	public void setIdMultimedia(Long idMultimedia) {
		this.idMultimedia = idMultimedia;
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

	public Long getIdMarcador() {
		return idMarcador;
	}

	public void setIdMarcador(Long idMarcador) {
		this.idMarcador = idMarcador;
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

	public int getEstadoPublicacion() {
		return estadoPublicacion;
	}

	public void setEstadoPublicacion(int estadoPublicacion) {
		this.estadoPublicacion = estadoPublicacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Boolean getTieneHistoria() {
		return tieneHistoria;
	}

	public void setTieneHistoria(Boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}

	public String getRutaMultimedia() {
		return rutaMultimedia;
	}

	public void setRutaMultimedia(String rutaMultimedia) {
		this.rutaMultimedia = rutaMultimedia;
	}

	public Long getIdEspecie() {
		return idEspecie;
	}

	public void setIdEspecie(Long idEspecie) {
		this.idEspecie = idEspecie;
	}

	public String getRutaIcono() {
		return rutaIcono;
	}

	public void setRutaIcono(String rutaIcono) {
		this.rutaIcono = rutaIcono;
	}	
						
}
