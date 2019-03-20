package co.gov.metropol.area247.model;

public class RutaConcretaDTO {

	private Long id;
	private String codigo;
	private String descripcion;
	/**
	 * Si la ruta concreta es Ruta'R' o Linea'L'
	 */
	private char tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

}
