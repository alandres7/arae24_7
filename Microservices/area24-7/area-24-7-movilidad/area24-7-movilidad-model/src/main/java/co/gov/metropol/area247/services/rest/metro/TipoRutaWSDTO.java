package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.model.TipoRutaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class TipoRutaWSDTO {

	@JsonProperty(value = "idTipoRuta")
	private Long idTipoRuta;

	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	public Long getIdTipoRuta() {
		return idTipoRuta;
	}

	public void setIdTipoRuta(Long idTipoRuta) {
		this.idTipoRuta = idTipoRuta;
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

	public TipoRutaDTO getTipoRutaDTO() {
		TipoRutaDTO tipoRutaDTO = new TipoRutaDTO();
		tipoRutaDTO.setNombre(nombre);
		tipoRutaDTO.setDescripcion(descripcion);
		tipoRutaDTO.setFuenteDatos(TipoViaje.METRO);
		return tipoRutaDTO;
	}

}
