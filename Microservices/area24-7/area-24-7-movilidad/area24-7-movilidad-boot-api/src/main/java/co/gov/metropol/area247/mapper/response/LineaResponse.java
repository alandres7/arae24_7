package co.gov.metropol.area247.mapper.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.LineaMetroDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class LineaResponse {

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

	@ApiModelProperty(value = "Horario de la línea", required = true)
	private List<HorarioLineaResponse> horarios;

	@ApiModelProperty(value = "Valor de la tarifa de la línea", required = true)
	private Double valorTarifaLinea;

	@ApiModelProperty(value = "Frecuencia de la línea", required = true)
	private List<FrecuenciaLineaResponse> frecuencia;
	

	@ApiModelProperty(value = "estaciones del metro", required = true)
	private List<EstacionLineaMetroResponse> estaciones;

	public LineaResponse(LineaMetroDTO linea) {
		this.horarios = new ArrayList<>();
		this.frecuencia = new ArrayList<>();
		this.estaciones = new ArrayList<>();
		this.idLinea = linea.getId();
		this.descripcion = linea.getDescripcion();

		this.coordenadas = GeometryUtil.toArrayString(linea.getCoordenadas().getCoordinates());

		Coordinate[] primerPunto = linea.getPrimerPunto().getCoordinates();
		this.primerPunto = new ArrayList<>();
		for (int i = 0; i < primerPunto.length; i++) {
			this.primerPunto.add(primerPunto[i].x);
			this.primerPunto.add(primerPunto[i].y);
		}

		Coordinate[] segundoPunto = linea.getUltimoPunto().getCoordinates();
		this.ultimoPunto = new ArrayList<>();
		for (int i = 0; i < segundoPunto.length; i++) {
			this.ultimoPunto.add(segundoPunto[i].x);
			this.ultimoPunto.add(segundoPunto[i].y);
		}
		this.activo = linea.getActivo();
		if (linea.getModoLinea() != null) {
			this.idModoLinea = Long.valueOf(linea.getModoLinea().indice());
			this.nombreModoLinea = linea.getModoLinea().name();
		}
		linea.getHorarioLineaMetro().iterator().forEachRemaining(horarioM -> {
			HorarioLineaResponse hora = new HorarioLineaResponse(horarioM);
			this.horarios.add(hora);
		});
		this.valorTarifaLinea = linea.getValorTarifa();
		linea.getFrecuenciaLineaMetro().iterator().forEachRemaining(frec -> {
			FrecuenciaLineaResponse frecu = new FrecuenciaLineaResponse(frec);
			this.frecuencia.add(frecu);
		});
		linea.getEstacionLineaMetro().iterator().forEachRemaining(estacion -> {
			EstacionLineaMetroResponse est = new EstacionLineaMetroResponse(estacion);
			est.setDescripcionLinea(descripcion);
			this.estaciones.add(est);
		});
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

	public List<HorarioLineaResponse> getHorario() {
		return horarios;
	}

	public void setHorario(List<HorarioLineaResponse> horario) {
		this.horarios = horario;
	}

	public Double getValorTarifaLinea() {
		return valorTarifaLinea;
	}

	public void setValorTarifaLinea(Double valorTarifaLinea) {
		this.valorTarifaLinea = valorTarifaLinea;
	}

	public List<FrecuenciaLineaResponse> getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(List<FrecuenciaLineaResponse> frecuencia) {
		this.frecuencia = frecuencia;
	}

	 public List<EstacionLineaMetroResponse> getEstaciones() {
	 return estaciones;
	 }
	
	 public void setEstaciones(List<EstacionLineaMetroResponse> estaciones) {
	 this.estaciones = estaciones;
	 }

}
