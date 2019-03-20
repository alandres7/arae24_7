package co.gov.metropol.area247.services.rest.metro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.model.ModoEstacionDTO;
import co.gov.metropol.area247.model.TipoModoTransporteDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ModoTransporteWSDTO {
	
	@JsonProperty(value = "nombre")
	private String nombre;
	
	@JsonProperty(value = "descripcion")
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public ModoEstacionDTO getModoEstacionDTO() {
		ModoEstacionDTO modoEstacionDTO = new ModoEstacionDTO();
		modoEstacionDTO.setNombre(getNombre());
		modoEstacionDTO.setDescripcion(getDescripcion());
		
		return modoEstacionDTO;
	}
	
	public TipoModoTransporteDTO getTipoModoTransporteDTO() {
		TipoModoTransporteDTO tipoModoTransporteDTO = new TipoModoTransporteDTO();
		tipoModoTransporteDTO.setNombre(getNombre());
		tipoModoTransporteDTO.setDescripcion(getDescripcion());
		tipoModoTransporteDTO.setFuenteDatos(TipoViaje.METRO);
		
		return tipoModoTransporteDTO;
	}

}
