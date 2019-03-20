package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RutaAndLineaResponse {

	@ApiModelProperty(value = "Identifica el resultado de la operación", required = true)
	private int codigo;

	@ApiModelProperty(value = "Mensaje que describe el resultado de la operación", required = true)
	private String descripcion;

	@ApiModelProperty(value = "lineas del metro", required = true)
	private List<LineaResponse> lineas;

	@ApiModelProperty(value = "Rutas de gtpc", required = true)
	private List<RutaGtpcResponse> rutas;
	
	public RutaAndLineaResponse() {}

	public RutaAndLineaResponse(List<LineaMetroDTO> lineasMetro) {
		this.lineas = new ArrayList<>();
		this.rutas = new ArrayList<>();
		lineasMetro.iterator().forEachRemaining(linea -> {
			LineaResponse line = new LineaResponse(linea);
			lineas.add(line);
			linea.getEstacionLineaMetro().iterator().forEachRemaining(estacion -> {
				EstacionLineaMetroResponse est = new EstacionLineaMetroResponse(estacion);
			});
		});
	}

	public RutaAndLineaResponse(List<LineaMetroDTO> lineas, List<RutaMetroDTO> rutas) {
		this.lineas = new ArrayList<>();
		this.rutas = new ArrayList<>();
		
		if (Utils.isNotEmpty(lineas)) {
			lineas.iterator().forEachRemaining(linea -> {
				LineaResponse lineaResponse = new LineaResponse(linea);
				this.lineas.add(lineaResponse);
			});
		}
		
		if (Utils.isNotEmpty(rutas)) {
			rutas.iterator().forEachRemaining(ruta -> {
				RutaGtpcResponse rutaResponse = new RutaGtpcResponse(ruta);
				this.rutas.add(rutaResponse);
			});
		}
		
	}

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

}
