package co.gov.metropol.area247.services.rest.metro;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class MetroWSDTO {

	@JsonProperty(value = "codigo")
	private int codigo;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "lineas")
	private List<LineaMetroWSDTO> lineas;
	
	@JsonProperty(value = "rutas")
	private List<RutaMetroWSDTO> rutas;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<LineaMetroWSDTO> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaMetroWSDTO> lineas) {
		this.lineas = lineas;
	}

	public List<LineaMetroDTO> getLineasDTO() {
		List<LineaMetroDTO> lineasMetroDTO = new ArrayList<>();
		getLineas().iterator().forEachRemaining(linea -> {
			lineasMetroDTO.add(linea.getLineaMetroDTO());
		});
		return lineasMetroDTO;
	}
	
	public List<RutaMetroWSDTO> getRutas() {
		return rutas;
	}

	public void setRutas(List<RutaMetroWSDTO> rutas) {
		this.rutas = rutas;
	}

	public List<RutaMetroDTO> getRutasDTO() {
		List<RutaMetroDTO> rutasDTO = new ArrayList<>();
		getRutas().iterator().forEachRemaining(ruta -> {
			rutasDTO.add(ruta.getRutaMetroDTO());
		});
		return rutasDTO;
	}

}
