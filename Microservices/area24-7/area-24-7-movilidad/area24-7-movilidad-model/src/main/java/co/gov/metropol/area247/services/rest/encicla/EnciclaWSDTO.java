package co.gov.metropol.area247.services.rest.encicla;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.ZonaCiudadEnciclaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class EnciclaWSDTO {

	@JsonProperty(value = "date")
	@Loggable
	private Long date;
	
	@JsonProperty(value = "stations")
	@Loggable
	private List<ZonaCiudadEnciclaWSDTO> stations;
	
	public EnciclaWSDTO(){
		super();
	}
	
	public EnciclaWSDTO(@JsonProperty(value = "date") Long date, @JsonProperty(value = "stations") List<ZonaCiudadEnciclaWSDTO> stations){
		this.date = date;
		this.stations = stations;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public List<ZonaCiudadEnciclaWSDTO> getStations() {
		return stations;
	}

	public void setStations(List<ZonaCiudadEnciclaWSDTO> stations) {
		this.stations = stations;
	}
	
	public List<ZonaCiudadEnciclaDTO> getZonasCiudadEncicla(){
		List<ZonaCiudadEnciclaDTO> zonasCiudadEncicla = new ArrayList<>();
		this.stations.forEach(zonaCiudad -> {
			ZonaCiudadEnciclaDTO zonaCiudadEnciclaDTO = new ZonaCiudadEnciclaDTO();
			zonaCiudadEnciclaDTO.setIdZonaCiudad(Long.parseLong(zonaCiudad.getId()));
			zonaCiudadEnciclaDTO.setNombre(zonaCiudad.getName());
			zonaCiudadEnciclaDTO.setDescripcion(zonaCiudad.getDesc());
			zonaCiudadEnciclaDTO.getEstacionesEncicla().addAll(zonaCiudad.getEstacionesEncicla());
			zonasCiudadEncicla.add(zonaCiudadEnciclaDTO);
		});
		return zonasCiudadEncicla;
	}
}
