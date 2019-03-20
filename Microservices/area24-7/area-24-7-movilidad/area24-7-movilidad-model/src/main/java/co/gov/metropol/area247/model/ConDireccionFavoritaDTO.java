package co.gov.metropol.area247.model;

import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class ConDireccionFavoritaDTO extends AbstractDTO {

	@Loggable
	private String nombre;

	@Loggable
	private String descripcion;

	@Loggable
	private Long idCoordenada;

	@Loggable
	private Long idUsuario;

	@Loggable
	private Point coordenada;
	
	@Loggable
	private ConCatUbicacionFavoritaDTO categoriaDTO;
	
	@Override
	public ConDireccionFavoritaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ConDireccionFavoritaDTO withEnabled(boolean enabled) {
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

	public ConCatUbicacionFavoritaDTO getCategoriaDTO() {
		return categoriaDTO;
	}

	public void setCategoriaDTO(ConCatUbicacionFavoritaDTO categoriaDTO) {
		this.categoriaDTO = categoriaDTO;
	}

}
