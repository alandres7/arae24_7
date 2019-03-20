package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class ParaderoRutaMetroDTO extends AbstractDTO {

	private Long idParadero;

	private String descripcion;

	private Double latitud;

	private Double longitud;

	private String codigo;

	private TipoParaderoDTO tipoParaderoDTO;

	private TipoOrientacionDTO tipoOrientacionDTO;

	private RutaMetroDTO rutaDTO;

	private Character activo;
	
	private int fuenteDatos;
	
	private Long sequencia;

	public ParaderoRutaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ParaderoRutaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdParadero() {
		return idParadero;
	}

	public ParaderoRutaMetroDTO setIdParadero(Long idParadero) {
		this.idParadero = idParadero;
		return this;
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

	public TipoParaderoDTO getTipoParaderoDTO() {
		return tipoParaderoDTO;
	}

	public void setTipoParaderoDTO(TipoParaderoDTO tipoParaderoDTO) {
		this.tipoParaderoDTO = tipoParaderoDTO;
	}

	public TipoOrientacionDTO getTipoOrientacionDTO() {
		return tipoOrientacionDTO;
	}

	public void setTipoOrientacionDTO(TipoOrientacionDTO tipoOrientacionDTO) {
		this.tipoOrientacionDTO = tipoOrientacionDTO;
	}

	public RutaMetroDTO getRutaDTO() {
		return rutaDTO;
	}

	public void setRutaDTO(RutaMetroDTO rutaDTO) {
		this.rutaDTO = rutaDTO;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public Long getSequencia() {
		return sequencia;
	}

	public void setSequencia(Long sequencia) {
		this.sequencia = sequencia;
	}

}
