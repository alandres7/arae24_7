package co.gov.metropol.area247.huellas.rest.response.msg;

public enum HuellasMsgs {
	
	SUCCESS_LIST(0L,"SUCCESS", "Se ha(n) recuperado tod@(s) l@(s) %s"),
	FAILURE_LIST(1L, "ERROR", "Error, recuperando el listado  de %s"),
	EMPTY_LIST(2L, "EMPTY", "No existen actividades"),
	SUCCESS(0L,"SUCCESS", "Se ha(n) encontrado y/o calculado l@(s) %s respectiva(s)"),
	FAILURE(1L, "ERROR", "Error, recuperando l@s %s asociad@(s) al ID dado."),
	EMPTY(2L, "EMPTY", "No existe(n) l@(s) %s respectiva(s) "),
	SUCCESS_CREATE(0L,"SUCCESS", "Se ha(n) creado l@(s) %s respectiv@(s)"),
	FAILURE_CREATE(1L, "ERROR", "Error, creando l@(s) %s respectiv@(s). "),
	SUCCESS_UPDATE(0L,"SUCCESS","l@(s) %s ha(n) sido satisfactoriamente actualizad@(s)"),
	FAILURE_UPDATE(1L,"ERROR","Falló la actualización de l@(s) %s"),
	SUCCESS_DELETE(0L,"SUCCESS","l@(s) %s ha(n) sido satisfactoriamente borrad@(s)"),
	FAILURE_DELETE(1L,"ERROR","Falló el borrado de l@(s) %s");
	
	private String status;
	private String descripcion;
	private Long code;
	/**
	 * @param status
	 * @param descripcion
	 * @param code
	 */
	private HuellasMsgs(Long code, String status, String descripcion) {
		this.status = status;
		this.descripcion = descripcion;
		this.code = code;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Long getCode() {
		return code;
	}
	
	public void setCode(Long code) {
		this.code = code;
	}
	
}
