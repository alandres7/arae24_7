package co.gov.metropol.area247.services.rest.metro;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.constants.TipoViaje;
import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.Utils;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class RutaMetroWSDTO {

	@JsonProperty(value = "idRuta")
	private Long idRuta;

	@JsonProperty(value = "codigo")
	private String codigo;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "longitud")
	private Double longitudRuta;

	@JsonProperty(value = "coordenadas")
	private List<CoordenadaWSDTO> coordenadas;

	@JsonProperty(value = "primerPunto")
	private CoordenadaWSDTO primerPunto;

	@JsonProperty(value = "ultimoPunto")
	private CoordenadaWSDTO ultimoPunto;

	@JsonProperty(value = "tiempoRecorrido")
	private Long tiempoEstimadoRuta;

	@JsonProperty(value = "modoRecorrido")
	private String modoRuta;

	@JsonProperty(value = "tipoRuta")
	private TipoRutaWSDTO tipoRuta;

	@JsonProperty(value = "tipoSistema")
	private TipoSistemaWSDTO tipoSistema;

	@JsonProperty(value = "activo")
	private Character activo;

	@JsonProperty(value = "paraderos")
	private List<ParaderoRutaMetroWSDTO> paraderos;

	@JsonProperty(value = "horarios")
	private List<HorarioRutaMetroWSDTO> horarios;

	@JsonProperty(value = "frecuencias")
	private List<FrecuenciaRutaMetroWSDTO> frecuencias;

	public RutaMetroWSDTO() {
		super();
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigoRuta(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getLongitudRuta() {
		return longitudRuta;
	}

	public void setLongitudRuta(Double longitudRuta) {
		this.longitudRuta = longitudRuta;
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

	public Long getTiempoEstimadoRuta() {
		return tiempoEstimadoRuta;
	}

	public void setTiempoEstimadoRuta(Long tiempoEstimadoRuta) {
		this.tiempoEstimadoRuta = tiempoEstimadoRuta;
	}

	public String getModoRuta() {
		return modoRuta;
	}

	public void setModoRuta(String modoRuta) {
		this.modoRuta = modoRuta;
	}

	public TipoRutaWSDTO getTipoRuta() {
		return tipoRuta;
	}

	public void setTipoRuta(TipoRutaWSDTO tipoRuta) {
		this.tipoRuta = tipoRuta;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public List<ParaderoRutaMetroWSDTO> getParaderos() {
		return paraderos;
	}

	public void setParaderos(List<ParaderoRutaMetroWSDTO> paraderos) {
		this.paraderos = paraderos;
	}

	public TipoSistemaWSDTO getTipoSistema() {
		return tipoSistema;
	}

	public void setTipoSistema(TipoSistemaWSDTO tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

	public List<HorarioRutaMetroWSDTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioRutaMetroWSDTO> horarios) {
		this.horarios = horarios;
	}

	public List<FrecuenciaRutaMetroWSDTO> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<FrecuenciaRutaMetroWSDTO> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public RutaMetroDTO getRutaMetroDTO() {
		RutaMetroDTO rutaMetroDTO = new RutaMetroDTO();
		rutaMetroDTO.setIdItem(getIdRuta());
		rutaMetroDTO.setCodigo(getCodigo());
		rutaMetroDTO.setDescripcion(getDescripcion());
		rutaMetroDTO.setLongitudRuta(getLongitudRuta());
		rutaMetroDTO.setCoordenadas(GeometryUtil.obtenerLineString(getCoordenadas().toArray(new CoordenadaWSDTO[0])));
		rutaMetroDTO.setPrimerPunto(GeometryUtil.obtenerPunto(getPrimerPunto()));
		rutaMetroDTO.setUltimoPunto(GeometryUtil.obtenerPunto(getUltimoPunto()));
		rutaMetroDTO.setTiempoEstimado(getTiempoEstimadoRuta());
		rutaMetroDTO.setModoRutaDTO(ModoRecorrido.valueOf(modoRuta));
		rutaMetroDTO.setRutaActiva(getActivo().toString());
		rutaMetroDTO.setFuenteDatos(TipoViaje.METRO);
		
		//rutaMetroDTO
			//	.setTipoSistemaRutaDTO(Utils.isNull(getTipoSistema()) ? null : getTipoSistema().getTipoSistemaDTO());
		rutaMetroDTO
				.setTipoRutaDTO(Utils.isNull(getTipoRuta()) ? null : getTipoRuta().getTipoRutaDTO());

		return rutaMetroDTO;
	}

}
