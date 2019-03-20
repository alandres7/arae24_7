package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.model.TareviaEstacionMetroDTO;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class EstacionResponse {

	@ApiModelProperty(value = "Identificador de la estación", required = true)
	private Long idEstacion;

	@ApiModelProperty(value = "Nombre de la estación", required = true)
	private String nombre;

	@ApiModelProperty(value = "Dirección de la estación", required = true)
	private String direccion;

	@ApiModelProperty(value = "Descripción de la estación", required = true)
	private String descripcion;

	@ApiModelProperty(value = "latitud de la estación", required = true)
	private Double latitud;

	@ApiModelProperty(value = "longitud de la estación", required = true)
	private Double longitud;

	@ApiModelProperty(value = "Estación activa", required = true)
	private String activo;

	@ApiModelProperty(value = "Identificador modo transporte de la estación", required = true)
	private Long idModoEstacion;

	@ApiModelProperty(value = "Nombre de la estación", required = true)
	private String nombreModoEstacion;

	@ApiModelProperty(value = "Tipo de la estación, M - A", required = true)
	private String tipoEstacion;

	@ApiModelProperty(value = "Linea que pertenece la estación", required = true)
	private Long idLinea;

	@ApiModelProperty(value = "Descripcion de la linea", required = true)
	private String descripcionLinea;
	
	@ApiModelProperty(value = "Lineas que pasan por esta estacion", required = true)
	private List<RutaInfoBasicaResponse> lineas; 

	public EstacionResponse(TareviaEstacionEnciclaDTO tareviaEstacionEncicla) {
		this.idEstacion = tareviaEstacionEncicla.getIdEstacionEncicla();
		this.nombre = tareviaEstacionEncicla.getNombreEstacionEncicla();
		this.descripcion = tareviaEstacionEncicla.getDescripcionEstacionEncicla();
		this.direccion = tareviaEstacionEncicla.getDireccionEstacionEncicla();
		this.latitud = tareviaEstacionEncicla.getLatitudEstacionEncila();
		this.longitud = tareviaEstacionEncicla.getLongitudEstacionEncila();
		this.activo = tareviaEstacionEncicla.getActivaEstacionEncicla();
		this.tipoEstacion = tareviaEstacionEncicla.getTipoEstacionEncicla();
		this.idLinea = null;
		this.descripcionLinea = null;
		
		if (!Utils.isNull(tareviaEstacionEncicla.getModoEstacionDTO())) {
			this.idModoEstacion = tareviaEstacionEncicla.getModoEstacionDTO().getId();
			this.nombreModoEstacion = tareviaEstacionEncicla.getModoEstacionDTO().getNombre();
		}
	}

	public EstacionResponse(TareviaEstacionMetroDTO tareviaEstacionMetro) {
		this.idEstacion = tareviaEstacionMetro.getId();
		this.nombre = tareviaEstacionMetro.getDescripcionEstacionMetro();
		this.descripcion = tareviaEstacionMetro.getDescripcionEstacionMetro();
		this.direccion = "";
		this.latitud = tareviaEstacionMetro.getLatitudEstacionMetro();
		this.longitud = tareviaEstacionMetro.getLongitudEstacionMetro();
		this.activo = tareviaEstacionMetro.getActivaEstacionMetro();
		this.idModoEstacion = tareviaEstacionMetro.getIdModoEstacion();
		this.tipoEstacion = tareviaEstacionMetro.getTipoEstacionMetro();
		this.nombreModoEstacion = "Metro";
		this.idLinea = null;
		this.descripcionLinea = null;
	}
	
	public EstacionResponse(EstacionLineaMetroDTO estacionLineaMetro) {
		this.idEstacion = estacionLineaMetro.getId();
		this.nombre = estacionLineaMetro.getCodigo();
		this.descripcion = estacionLineaMetro.getDescripcion();
		this.direccion = "";
		this.latitud = estacionLineaMetro.getLatitud();
		this.longitud = estacionLineaMetro.getLongitud();
		this.activo = estacionLineaMetro.getActivo().toString();
		this.tipoEstacion = estacionLineaMetro.getTipoEstacion();
		this.idLinea = estacionLineaMetro.getLineaDTO().getId();
		this.descripcionLinea = estacionLineaMetro.getLineaDTO().getDescripcion();
		this.getLineas().add(new RutaInfoBasicaResponse(estacionLineaMetro.getLineaDTO()));
		
		if (!Utils.isNull(estacionLineaMetro.getModoEstacionDTO())) {
			this.idModoEstacion = (long)estacionLineaMetro.getModoEstacionDTO().indice();
			this.nombreModoEstacion = estacionLineaMetro.getModoEstacionDTO().name();
		}
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
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

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdModoEstacion() {
		return idModoEstacion;
	}

	public void setIdModoEstacion(Long idModoEstacion) {
		this.idModoEstacion = idModoEstacion;
	}

	public String getNombreModoEstacion() {
		return nombreModoEstacion;
	}

	public void setNombreModoEstacion(String nombreModoEstacion) {
		this.nombreModoEstacion = nombreModoEstacion;
	}

	public String getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(String tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	public String getDescripcionLinea() {
		return descripcionLinea;
	}

	public void setDescripcionLinea(String descripcionLinea) {
		this.descripcionLinea = descripcionLinea;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<RutaInfoBasicaResponse> getLineas() {
		if (this.lineas == null) { 
			lineas = new ArrayList<>();
		} 
		return lineas;
	}

	public void setLineas(List<RutaInfoBasicaResponse> lineas) {
		this.lineas = lineas;
	}

}
