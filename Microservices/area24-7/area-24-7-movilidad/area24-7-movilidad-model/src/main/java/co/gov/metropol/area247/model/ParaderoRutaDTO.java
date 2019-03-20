package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class ParaderoRutaDTO extends AbstractDTO {

	private Long idItem;
	
	private int fuenteDatos;
	
	private String descripcion;

	private Double latitud;

	private Double longitud;

	private String codigo;

	private Long idTipoParadero;

	private Long idTipoOrientacion;

	private Long idRuta;

	private String activo;

	public ParaderoRutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ParaderoRutaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdTipoParadero() {
		return idTipoParadero;
	}

	public void setIdTipoParadero(Long idTipoParadero) {
		this.idTipoParadero = idTipoParadero;
	}

	public Long getIdTipoOrientacion() {
		return idTipoOrientacion;
	}

	public void setIdTipoOrientacion(Long idTipoOrientacion) {
		this.idTipoOrientacion = idTipoOrientacion;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
    
}
