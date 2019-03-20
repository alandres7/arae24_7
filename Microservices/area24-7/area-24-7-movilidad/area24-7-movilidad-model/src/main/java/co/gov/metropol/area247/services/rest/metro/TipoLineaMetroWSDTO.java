package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.TipoLineaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class TipoLineaMetroWSDTO {

	@JsonProperty(value = "idTipoLinea")
	private Long idTipoLinea;

	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	public Long getIdTipoLinea() {
		return idTipoLinea;
	}

	public void setIdTipoLinea(Long idTipoLinea) {
		this.idTipoLinea = idTipoLinea;
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

	public TipoLineaDTO getTipoLineaDTO() {
		TipoLineaDTO tipoLineaDTO = new TipoLineaDTO();
		tipoLineaDTO.setIdTipoLinea(idTipoLinea);
		tipoLineaDTO.setNombre(nombre);
		tipoLineaDTO.setDescripcion(descripcion);
		return tipoLineaDTO;
	}

}
