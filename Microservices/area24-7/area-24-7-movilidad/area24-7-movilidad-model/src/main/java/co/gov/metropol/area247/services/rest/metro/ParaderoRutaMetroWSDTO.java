package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.util.Utils;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ParaderoRutaMetroWSDTO {

	@JsonProperty(value = "idParadero")
	private String idParadero;

	@JsonProperty(value = "codigo")
	private String codigo;
	
	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "latitud")
	private Double latitud;

	@JsonProperty(value = "longitud")
	private Double longitud;

	@JsonProperty(value = "tipoParadero")
	private TipoParaderoWSDTO tipoParadero;

	@JsonProperty(value = "tipoOrientacion")
	private TipoOrientacionWSDTO tipoOrientacion;

	@JsonProperty(value = "activo")
	private Character activo;

	public String getIdParadero() {
		return idParadero;
	}

	public void setIdParadero(String idParadero) {
		this.idParadero = idParadero;
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

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public TipoParaderoWSDTO getTipoParadero() {
		return tipoParadero;
	}

	public void setTipoParadero(TipoParaderoWSDTO tipoParadero) {
		this.tipoParadero = tipoParadero;
	}

	public TipoOrientacionWSDTO getTipoOrientacion() {
		return tipoOrientacion;
	}

	public void setTipoOrientacion(TipoOrientacionWSDTO tipoOrientacion) {
		this.tipoOrientacion = tipoOrientacion;
	}

	public ParaderoRutaMetroDTO getParaderoRutaMetroDTO() {
		ParaderoRutaMetroDTO paraderoRutaMetroDTO = new ParaderoRutaMetroDTO();
		paraderoRutaMetroDTO.setDescripcion(getDescripcion());
		paraderoRutaMetroDTO.setLatitud(getLatitud());
		paraderoRutaMetroDTO.setLongitud(getLongitud());
		paraderoRutaMetroDTO.setCodigo(getCodigo());
		paraderoRutaMetroDTO.setTipoParaderoDTO(Utils.isNull(getTipoParadero()) ? null : getTipoParadero().getTipoParaderoDTO());
		paraderoRutaMetroDTO.setTipoOrientacionDTO(Utils.isNull(getTipoOrientacion()) ? null : getTipoOrientacion().getTipoOrientacionDTO());
		paraderoRutaMetroDTO.setActivo(getActivo());
		paraderoRutaMetroDTO.setFuenteDatos(TipoViaje.METRO);
		return paraderoRutaMetroDTO;
	}

}
