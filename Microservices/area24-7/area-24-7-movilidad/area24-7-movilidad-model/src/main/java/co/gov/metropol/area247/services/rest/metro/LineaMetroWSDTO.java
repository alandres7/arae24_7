package co.gov.metropol.area247.services.rest.metro;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class LineaMetroWSDTO {

	@JsonProperty(value = "idLinea")
	private Long idLinea;

	@JsonProperty(value = "codigo")
	private String codigo;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "longitud")
	private Double longitud;

	@JsonProperty(value = "coordenadas")
	private List<CoordenadaWSDTO> coordenadas;

	@JsonProperty(value = "primerPunto")
	private CoordenadaWSDTO primerPunto;

	@JsonProperty(value = "ultimoPunto")
	private CoordenadaWSDTO ultimoPunto;

	@JsonProperty(value = "tiempoRecorrido")
	private Long tiempoRecorrido;

	@JsonProperty(value = "modoLinea")
	private String modoLinea;

	@JsonProperty(value = "nombreModo")
	private String nombreModo;

	@JsonProperty(value = "activo")
	private Character activo;

	@JsonProperty(value = "frecuencias")
	private List<FrecuenciaLineaMetroWSDTO> frecuencias;

	@JsonProperty(value = "horarios")
	private List<HorarioLineaMetroWSDTO> horarios;

	@JsonProperty(value = "estaciones")
	private List<EstacionMetroWSDTO> estaciones;

	public LineaMetroWSDTO() {
		super();
	}

	public LineaMetroWSDTO(@JsonProperty(value = "idlinea") Long idLinea,
			@JsonProperty(value = "codigolinea") String codigo, @JsonProperty(value = "descripcion") String descripcion,
			@JsonProperty(value = "longitudlinea") Double longitud,
			@JsonProperty(value = "coordenadas") List<CoordenadaWSDTO> coordenadas,
			@JsonProperty(value = "primerpunto") CoordenadaWSDTO primerPunto,
			@JsonProperty(value = "ultimopunto") CoordenadaWSDTO ultimoPunto,
			@JsonProperty(value = "idtipolinea") TipoLineaMetroWSDTO tipoLinea,
			@JsonProperty(value = "tiempoestimadolinea") Long tiempoRecorrido,
			@JsonProperty(value = "modoRecorrido") String modoLinea,
			@JsonProperty(value = "nombreModo") String nombreModo, @JsonProperty(value = "activo") Character activo,
			@JsonProperty(value = "frecuencias") List<FrecuenciaLineaMetroWSDTO> frecuencias,
			@JsonProperty(value = "horarios") List<HorarioLineaMetroWSDTO> horarios,
			@JsonProperty(value = "estaciones") List<EstacionMetroWSDTO> estaciones) {

		this.idLinea = idLinea;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.coordenadas = coordenadas;
		this.primerPunto = primerPunto;
		this.ultimoPunto = ultimoPunto;
		this.tiempoRecorrido = tiempoRecorrido;
		this.modoLinea = modoLinea;
		this.nombreModo = nombreModo;
		this.activo = activo;
		this.frecuencias = frecuencias;
		this.horarios = horarios;
		this.estaciones = estaciones;

	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
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

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public List<CoordenadaWSDTO> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<CoordenadaWSDTO> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public CoordenadaWSDTO getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(CoordenadaWSDTO primerPunto) {
		this.primerPunto = primerPunto;
	}

	public CoordenadaWSDTO getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(CoordenadaWSDTO ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public String getModoLinea() {
		return modoLinea;
	}

	public void setModoLinea(String modoLinea) {
		this.modoLinea = modoLinea;
	}

	public String getNombreModo() {
		return nombreModo;
	}

	public void setNombreModo(String nombreModo) {
		this.nombreModo = nombreModo;
	}

	public Long getTiempoRecorrido() {
		return tiempoRecorrido;
	}

	public void setTiempoRecorrido(Long tiempoRecorrido) {
		this.tiempoRecorrido = tiempoRecorrido;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public List<FrecuenciaLineaMetroWSDTO> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<FrecuenciaLineaMetroWSDTO> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public List<HorarioLineaMetroWSDTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioLineaMetroWSDTO> horarios) {
		this.horarios = horarios;
	}

	public List<EstacionMetroWSDTO> getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(List<EstacionMetroWSDTO> estaciones) {
		this.estaciones = estaciones;
	}

	public LineaMetroDTO getLineaMetroDTO() {
		LineaMetroDTO lineaMetroDTO = new LineaMetroDTO();
		lineaMetroDTO.setIdLinea(getIdLinea());
		lineaMetroDTO.setCodigo(getCodigo());
		lineaMetroDTO.setDescripcion(getDescripcion());
		lineaMetroDTO.setLongitud(getLongitud());
		lineaMetroDTO.setCoordenadas(GeometryUtil.obtenerLineString(getCoordenadas().toArray(new CoordenadaWSDTO[0])));
		lineaMetroDTO.setPrimerPunto(GeometryUtil.obtenerPunto(getPrimerPunto()));
		lineaMetroDTO.setUltimoPunto(GeometryUtil.obtenerPunto(getUltimoPunto()));
		lineaMetroDTO.setTiempoEstimado(getTiempoRecorrido());
		lineaMetroDTO.setModoLinea(ModoRecorrido.valueOf(modoLinea));
		lineaMetroDTO.setActivo(getActivo());

		return lineaMetroDTO;
	}

}
