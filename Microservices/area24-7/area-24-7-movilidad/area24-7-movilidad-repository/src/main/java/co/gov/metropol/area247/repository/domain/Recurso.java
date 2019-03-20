package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "TSIMAUD_RECURSOS", schema = "MOVILIDAD")
public class Recurso extends Entities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE", nullable = false, length = 200)
	private String nombre;
	
	@Column(name = "S_CODIGO", nullable = false, length = 20)
	private String codigo;
	
	@Lob
	@Column(name = "S_URL", nullable = false)
	private String url;
	
	@Lob
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Override
	public Recurso withId(Long id) {
		super.setId(id);
		return this;
	}
	
	@Override
	public Recurso withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public Recurso setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getCodigo() {
		return codigo;
	}

	public Recurso setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Recurso setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Recurso setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

}