package co.gov.metropol.area247.mapper.response;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.Lists;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.util.Utils;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class LineaMetroResponse {

	@ApiModelProperty(value = "Identificador unico de la linea", required = true)
	private Long idLinea;

	@ApiModelProperty(value = "nombre de la linea", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Coordenadas de la línea", required = true)
	private String[] coordenadas;

	@ApiModelProperty(value = "Coordenadas del primer punto de la línea", required = true)
	private List<Double> primerPunto;

	@ApiModelProperty(value = "Indica si la línea se encuentra activa o no", required = true)
	private List<Double> ultimoPunto;

	@ApiModelProperty(value = "Coordenadas del ultimo punto de la línea", required = true)
	private Character activo;

	@ApiModelProperty(value = "Identificador del modo de transporte al que pertenece la línea", required = true)
	private Long idModoLinea;

	@ApiModelProperty(value = "Nombre del modo de transporte al que pertenece la línea", required = true)
	private String nombreModoLinea;

	@ApiModelProperty(value = "Valor de la tarifa de la línea", required = true)
	private Double valorTarifaLinea;

	@ApiModelProperty(value = "Cantidad de vehículos que pasan por hora", required = true)
	private List<FrecuenciaLineaResponse> frecuencias;

	@ApiModelProperty(value = "Horas de operación de las rutas", required = true)
	private List<HorarioLineaResponse> horarios;

	public LineaMetroResponse(LineaMetroDTO linea) {

		this.idLinea = linea.getId();
		this.descripcion = linea.getDescripcion();
		this.coordenadas = GeometryUtil.toArrayString(linea.getCoordenadas());

		if (this.coordenadas != null && this.coordenadas.length >= 2) {
			final int tamCoordenadas = coordenadas.length;
			this.primerPunto = Arrays.asList(Double.valueOf(this.coordenadas[0]), Double.valueOf(this.coordenadas[1]));
			this.ultimoPunto = Arrays.asList(Double.valueOf(this.coordenadas[tamCoordenadas - 2]),
					Double.valueOf(this.coordenadas[tamCoordenadas - 1]));
		}

		this.activo = linea.getActivo();
		this.idModoLinea = Long.valueOf(linea.getModoLinea().indice());
		this.valorTarifaLinea = linea.getValorTarifa();

		this.frecuencias = Lists.newArrayList();
		if (Utils.isNotEmpty(linea.getFrecuenciaLineaMetro())) {
			for (FrecuenciaLineaMetroDTO frecuenciaDTO : linea.getFrecuenciaLineaMetro()) {
				FrecuenciaLineaResponse frecuencialineaResponse = new FrecuenciaLineaResponse(frecuenciaDTO);
				frecuencias.add(frecuencialineaResponse);
			}
		}

		this.horarios = Lists.newArrayList();
		if (Utils.isNotEmpty(linea.getHorarioLineaMetro())) {
			for (HorarioLineaMetroDTO horarioDTO : linea.getHorarioLineaMetro()) {
				HorarioLineaResponse horarioLineaResponse = new HorarioLineaResponse(horarioDTO);
				horarios.add(horarioLineaResponse);
			}
		}
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String[] coordenadas) {
		this.coordenadas = coordenadas;
	}

	public List<Double> getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(List<Double> primerPunto) {
		this.primerPunto = primerPunto;
	}

	public List<Double> getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(List<Double> ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public Long getIdModoLinea() {
		return idModoLinea;
	}

	public void setIdModoLinea(Long idModoLinea) {
		this.idModoLinea = idModoLinea;
	}

	public String getNombreModoLinea() {
		return nombreModoLinea;
	}

	public void setNombreModoLinea(String nombreModoLinea) {
		this.nombreModoLinea = nombreModoLinea;
	}

	public Double getValorTarifaLinea() {
		return valorTarifaLinea;
	}

	public void setValorTarifaLinea(Double valorTarifaLinea) {
		this.valorTarifaLinea = valorTarifaLinea;
	}

	public List<FrecuenciaLineaResponse> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<FrecuenciaLineaResponse> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public List<HorarioLineaResponse> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioLineaResponse> horarios) {
		this.horarios = horarios;
	}

}
