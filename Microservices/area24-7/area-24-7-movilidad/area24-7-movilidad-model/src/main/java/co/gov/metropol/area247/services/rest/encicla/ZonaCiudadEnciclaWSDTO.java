package co.gov.metropol.area247.services.rest.encicla;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.EstacionEnciclaDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ZonaCiudadEnciclaWSDTO {
	
	@JsonProperty(value = "id")
	@Loggable
	private String id;
	
	@JsonProperty(value = "name")
	@Loggable
	private String name;
	
	@JsonProperty(value = "desc")
	@Loggable
	private String desc;
	
	@JsonProperty(value = "items")
	@Loggable
	private List<EstacionEnciclaWSDTO> items;
	
	public ZonaCiudadEnciclaWSDTO(){
		super();
	}
	
	public ZonaCiudadEnciclaWSDTO(@JsonProperty(value = "id") String id, 
			@JsonProperty(value = "name") String name, 
			@JsonProperty(value = "desc") String desc, 
			@JsonProperty(value = "items") List<EstacionEnciclaWSDTO> items){
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.items = items;   
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<EstacionEnciclaWSDTO> getItems() {
		return items;
	}

	public void setItems(List<EstacionEnciclaWSDTO> items) {
		this.items = items;
	}
	
	public Set<EstacionEnciclaDTO> getEstacionesEncicla(){
		Set<EstacionEnciclaDTO> estacionesEncicla = new HashSet<>();
		this.items.forEach(estacion -> {
			EstacionEnciclaDTO estacionEnciclaDTO = new EstacionEnciclaDTO();
			estacionEnciclaDTO.setIdEstacion(Long.parseLong(estacion.getId()));
			estacionEnciclaDTO.setNombre(estacion.getName());
			estacionEnciclaDTO.setDescripcion(estacion.getDescription());
			estacionEnciclaDTO.setDireccion(estacion.getAddress());
			estacionEnciclaDTO.setLatitud(Double.parseDouble(estacion.getLat()));
			estacionEnciclaDTO.setLongitud(Double.parseDouble(estacion.getLon()));
			estacionEnciclaDTO.setTipo(estacion.getType());
			estacionEnciclaDTO.setCapacidad(estacion.getCapacity());
			estacionEnciclaDTO.setCiclas(estacion.getBikes());
			estacionEnciclaDTO.setLugares(estacion.getPlaces());
			estacionEnciclaDTO.setImagen(estacion.getPicture());
			estacionEnciclaDTO.setOrden(estacion.getOrder());
			estacionesEncicla.add(estacionEnciclaDTO);
		});
		return estacionesEncicla;
	}
}
