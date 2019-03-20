package co.gov.metropol.area247.mapper.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RutaOrLineaInfoResponse {

	@ApiModelProperty(value = "Identifica el resultado de la operación", required = true)
	private int codigo;

	@ApiModelProperty(value = "Mensaje que describe el resultado de la operación", required = true)
	private String descripcion;

	@ApiModelProperty(value = "linea del metro", required = true)
	private LineaResponse linea;

	@ApiModelProperty(value = "Ruta de gtpc", required = true)
	private RutaGtpcResponse ruta;

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

	public LineaResponse getLinea() {
		return linea;
	}

	public void setLinea(LineaResponse linea) {
		this.linea = linea;
	}

	public RutaGtpcResponse getRuta() {
		return ruta;
	}

	public void setRuta(RutaGtpcResponse ruta) {
		this.ruta = ruta;
	}

}
