package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.model.TipoOrientacionDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class TipoOrientacionWSDTO {

	@JsonProperty(value = "codigo")
	private String nombre;

	@JsonProperty(value = "descripcion")
	private String descripcion;

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

	public TipoOrientacionDTO getTipoOrientacionDTO() {
		TipoOrientacionDTO tipoOrientacionDTO = new TipoOrientacionDTO();
		tipoOrientacionDTO.setNombre(getNombre());
		tipoOrientacionDTO.setDescripcion(getDescripcion());
		tipoOrientacionDTO.setFuenteDatos(TipoViaje.METRO);
		return tipoOrientacionDTO;
	}

}
