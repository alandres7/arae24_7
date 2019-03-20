package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class TipoSistemaWSDTO {

	@JsonProperty(value = "idTipoSistema")
	private Long idTipoSistema;

	@JsonProperty(value = "codigo")
	private String nombre;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	public Long getIdTipoSistema() {
		return idTipoSistema;
	}

	public void setIdTipoSistema(Long idTipoSistema) {
		this.idTipoSistema = idTipoSistema;
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

	public TipoSistemaRutaDTO getTipoSistemaDTO() {
		TipoSistemaRutaDTO tipoSistemaRutaDTO = new TipoSistemaRutaDTO();
		tipoSistemaRutaDTO.setNombre(nombre);
		tipoSistemaRutaDTO.setDescripcion(descripcion);
		tipoSistemaRutaDTO.setFuenteDatos(TipoViaje.METRO);
		return tipoSistemaRutaDTO;
	}

}
