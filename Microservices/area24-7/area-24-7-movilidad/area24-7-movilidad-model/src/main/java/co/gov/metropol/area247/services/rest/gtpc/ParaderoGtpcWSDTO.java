package co.gov.metropol.area247.services.rest.gtpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.TipoParaderoDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ParaderoGtpcWSDTO {

	@JsonProperty(value = "idparadero")
	private Long idparadero;
	
	@JsonProperty(value = "descripcion")
	private String descripcion;
	
	@JsonProperty(value = "latitud")
	private Double latitud;
	
	@JsonProperty(value = "longitud")
	private Double longitud;
	
	@JsonProperty(value = "codigoParadero")
	private String codigoParadero;
	
	@JsonProperty(value = "idTipoParadeto")
	private Long idTipoParadero;
	
	@JsonProperty(value = "nombreTipoParadero")
	private String nombreTipoParadero;
	
	@JsonProperty(value = "idTipoOrientacion")
	private Long idTipoOrientacion;
	
	@JsonProperty(value = "nombreTipoOrientacion")
	private String nombreTipoOrientacion;
	
	@JsonProperty(value = "activo")
	private String activo;
	
	@JsonProperty(value = "idRuta")
	private Long idRuta;

	public Long getIdparadero() {
		return idparadero;
	}

	public void setIdparadero(Long idparadero) {
		this.idparadero = idparadero;
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

	public String getCodigoparadero() {
		return codigoParadero;
	}

	public void setCodigoparadero(String codigoparadero) {
		this.codigoParadero = codigoparadero;
	}

	public Long getIdtipoparadero() {
		return idTipoParadero;
	}

	public void setIdtipoparadero(Long idtipoparadero) {
		this.idTipoParadero = idtipoparadero;
	}

	public String getNombretipoparadero() {
		return nombreTipoParadero;
	}

	public void setNombretipoparadero(String nombretipoparadero) {
		this.nombreTipoParadero = nombretipoparadero;
	}

	public Long getIdorientacion() {
		return idTipoOrientacion;
	}

	public void setIdorientacion(Long idorientacion) {
		this.idTipoOrientacion = idorientacion;
	}

	public String getNombretipoorientacion() {
		return nombreTipoOrientacion;
	}

	public void setNombretipoorientacion(String nombretipoorientacion) {
		this.nombreTipoOrientacion = nombretipoorientacion;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdruta() {
		return idRuta;
	}

	public void setIdruta(Long idruta) {
		this.idRuta = idruta;
	}
	
	public ParaderoRutaDTO getParaderoRuta() {
		ParaderoRutaDTO paraderoRuta = new ParaderoRutaDTO();
		paraderoRuta.setIdItem(this.idparadero);
		paraderoRuta.setFuenteDatos(2);
		paraderoRuta.setDescripcion(this.descripcion);
		paraderoRuta.setLatitud(this.latitud);
		paraderoRuta.setLongitud(this.longitud);
		paraderoRuta.setCodigo(this.codigoParadero);
		paraderoRuta.setIdTipoParadero(this.idTipoParadero);
		paraderoRuta.setIdTipoOrientacion(this.idTipoOrientacion);
		paraderoRuta.setIdRuta(this.idRuta);
		paraderoRuta.setActivo(this.activo);
		return paraderoRuta;
	}
	
	public TipoParaderoDTO getTipoParaderoDTO() {
		TipoParaderoDTO tipoParaderoDTO = new TipoParaderoDTO();
		tipoParaderoDTO.setIdItem(this.idTipoParadero);
		tipoParaderoDTO.setFuenteDatos(2);
		tipoParaderoDTO.setNombre(this.nombreTipoParadero);
		tipoParaderoDTO.setDescripcion("-");
		return tipoParaderoDTO;
	}
	
	public TipoOrientacionDTO getTipoOrientacionDTO() {
		TipoOrientacionDTO tipoOrientacionDTO = new TipoOrientacionDTO();
		tipoOrientacionDTO.setIdItem(this.idTipoOrientacion);
		tipoOrientacionDTO.setFuenteDatos(2);
		tipoOrientacionDTO.setNombre(this.nombreTipoOrientacion);
		tipoOrientacionDTO.setDescripcion("-");
		return tipoOrientacionDTO;
	}
		
}
