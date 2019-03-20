package co.gov.metropol.area247.contenedora.model.dto;

public class MensajeDto {
	
	private Long id;
	private String descripcion;
	private String titulo;
	private String nombreIdentificador;
	private String uso;
	private Long idAplicacion;
	private String nombreAplicacion;
	

	public MensajeDto(Long id, String descripcion, String titulo, 
			String nombreIdentificador, String uso,Long idAplicacion) {
		this.id = id;
		this.descripcion = descripcion;
		this.titulo = titulo;
		this.nombreIdentificador = nombreIdentificador;
		this.uso = uso;
		this.idAplicacion = idAplicacion;				
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
	
	public void setDescripcion(String contenido) {
		this.descripcion = contenido;
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