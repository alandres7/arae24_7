package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247CON_DIRECCION_FAVORITA", schema = "CONTENEDOR")
public class ConDireccionFavorita extends Entities {

	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE", nullable = false, length = 200)
	private String nombre;

	@Column(name = "S_DESCRIPCION", length = 300)
	private String descripcion;

	@Column(name = "ID_COORDENADA")
	private Long idCoordenada;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "COORDENADA")
	private Point coordenada;
	
	@Column(name = "ID_CAT_DIR_FAVORITA")
	private Long idCategoria;

	@Override
	public ConDireccionFavorita withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ConDireccionFavorita withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
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

	public Long getIdCoordenada() {
		return idCoordenada;
	}

	public void setIdCoordenada(Long idCoordenada) {
		this.idCoordenada = idCoordenada;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Point getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Point coordenada) {
		this.coordenada = coordenada;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	@Override
	public boolean isNew() {
		return (this.id == null);
	}

}
