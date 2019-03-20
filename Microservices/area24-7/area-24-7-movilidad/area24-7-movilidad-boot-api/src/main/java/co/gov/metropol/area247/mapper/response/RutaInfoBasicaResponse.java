package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RutaInfoBasicaResponse {

	@ApiModelProperty(value = "Codigo de la ruta o linea", required = true)
	private String codigo;

	@ApiModelProperty(value = "Descripción de la ruta o linea", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Valor de la tarifa de la ruta.", required = true)
	private Double tarifa;

	@ApiModelProperty(value = "Horas de operación de las rutas", required = true)
	private List<HorarioRutaResponse> horariosRuta;
	
	@ApiModelProperty(value = "Horas de operación de las lineas", required = true)
	private List<HorarioLineaResponse> horariosLinea;

	@ApiModelProperty(value = "Nombre de la empresa a la que pertenece la ruta.", required = true)
	private List<String> empresas;

	public RutaInfoBasicaResponse() {}
	
	public RutaInfoBasicaResponse(RutaMetroDTO rutaMetroDTO) {
		this.codigo = rutaMetroDTO.getCodigo();
		this.descripcion = rutaMetroDTO.getDescripcion();
		this.tarifa = rutaMetroDTO.getValorTarifa();
		this.horariosRuta = rutaMetroDTO.getHorariosDTO().stream().map(i -> new HorarioRutaResponse(i))
				.collect(Collectors.toList());
	}
	
	public RutaInfoBasicaResponse(LineaMetroDTO lineaMetroDTO) {
		this.codigo = lineaMetroDTO.getCodigo();
		this.descripcion = lineaMetroDTO.getDescripcion();
		this.tarifa = lineaMetroDTO.getValorTarifa();
		this.horariosLinea = lineaMetroDTO.getHorarioLineaMetro().stream().map(i -> new HorarioLineaResponse(i))
				.collect(Collectors.toList());
	}
	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	public List<HorarioRutaResponse> getHorariosRuta() {
		if (null == this.horariosRuta) {
			this.horariosRuta = new ArrayList<>();
		}
		return horariosRuta;
	}

	public void setHorariosRuta(List<HorarioRutaResponse> horarios) {
		this.horariosRuta = horarios;
	}
	
	public List<HorarioLineaResponse> getHorariosLinea() {
		if (null == this.horariosLinea) {
			this.horariosLinea = new ArrayList<>();
		}
		return horariosLinea;
	}

	public void setHorariosLinea(List<HorarioLineaResponse> horarios) {
		this.horariosLinea = horarios;
	}

	public List<String> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<String> empresas) {
		this.empresas = empresas;
	}

}
