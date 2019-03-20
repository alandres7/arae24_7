package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ParaderoResponse {

	@ApiModelProperty(value = "Identificador del paradero", required = true)
	private Long idParadero;

	@ApiModelProperty(value = "Identificador de la ruta", required = true)
	private Long idRuta;

	@ApiModelProperty(value = "Descripción del paradero", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Latitud del paradero", required = true)
	private double latitud;

	@ApiModelProperty(value = "Longitud del paradero", required = true)
	private double longitud;

	@ApiModelProperty(value = "Codigo del paradero", required = true)
	private String codigoParadero;

	@ApiModelProperty(value = "Nombre del tipo de paradero", required = true)
	private String nombreTipoParadero;

	@ApiModelProperty(value = "Nombre del tipo de orientación de paradero", required = true)
	private String nombreTipoOrientacion;

	@ApiModelProperty(value = "Paradero activo S/N", required = true)
	private String activo;
	
	@ApiModelProperty(value = "Rutas que pasan por este paradero", required = true)
	private List<RutaInfoBasicaResponse> rutas;

	public ParaderoResponse(ParaderoRutaDTO paraderoRutaDTO, TipoParaderoDTO tipoParaderoDTO,
			TipoOrientacionDTO tipoOrientacionParaderoDTO) {
		this.idParadero = paraderoRutaDTO.getId();
		this.idRuta = paraderoRutaDTO.getIdRuta();
		this.descripcion = paraderoRutaDTO.getDescripcion();
		this.latitud = paraderoRutaDTO.getLatitud();
		this.longitud = paraderoRutaDTO.getLongitud();
		this.codigoParadero = paraderoRutaDTO.getCodigo();
		this.nombreTipoParadero = "-";
		this.nombreTipoOrientacion = "-";
		this.activo = paraderoRutaDTO.getActivo();
	}
	
	public ParaderoResponse(ParaderoRutaMetroDTO paraderoRutaMetroDTO) {
		this.idParadero = paraderoRutaMetroDTO.getId();
		this.idRuta = paraderoRutaMetroDTO.getRutaDTO().getId();
		this.descripcion = paraderoRutaMetroDTO.getDescripcion();
		this.latitud = paraderoRutaMetroDTO.getLatitud();
		this.longitud = paraderoRutaMetroDTO.getLongitud();
		this.codigoParadero = paraderoRutaMetroDTO.getCodigo();
		this.nombreTipoParadero = Utils.isNull(paraderoRutaMetroDTO.getTipoParaderoDTO()) ? null
				: paraderoRutaMetroDTO.getTipoParaderoDTO().getNombre();
		this.nombreTipoOrientacion = paraderoRutaMetroDTO.getTipoOrientacionDTO().getNombre();
		this.activo = paraderoRutaMetroDTO.getActivo().toString();
		this.getRutas().add(new RutaInfoBasicaResponse(paraderoRutaMetroDTO.getRutaDTO()));
	}

	public Long getIdParadero() {
		return idParadero;
	}

	public void setIdParadero(Long idParadero) {
		this.idParadero = idParadero;
	}

	public Long getIdRura() {
		return idRuta;
	}

	public void setIdRura(Long idRura) {
		this.idRuta = idRura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getCodigoParadero() {
		return codigoParadero;
	}

	public void setCodigoParadero(String codigoParadero) {
		this.codigoParadero = codigoParadero;
	}

	public String getNombreTipoParadero() {
		return nombreTipoParadero;
	}

	public void setNombreTipoParadero(String nombreTipoParadero) {
		this.nombreTipoParadero = nombreTipoParadero;
	}

	public String getNombreTipoOrientacion() {
		return nombreTipoOrientacion;
	}

	public void setNombreTipoOrientacion(String nombreTipoOrientacion) {
		this.nombreTipoOrientacion = nombreTipoOrientacion;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public List<RutaInfoBasicaResponse> getRutas() {
		if (null == this.rutas) {
			this.rutas = new ArrayList<>();
		}
		return rutas;
	}

	public void setRutas(List<RutaInfoBasicaResponse> rutas) {
		this.rutas = rutas;
	}

}
