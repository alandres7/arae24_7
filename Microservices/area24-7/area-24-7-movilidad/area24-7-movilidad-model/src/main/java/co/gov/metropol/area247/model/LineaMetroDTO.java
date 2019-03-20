package co.gov.metropol.area247.model;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.Utils;

public class LineaMetroDTO extends AbstractDTO {

	private Long idLinea;

	private String codigo;

	private String descripcion;

	private Double longitud;

	private LineString coordenadas;

	private Point primerPunto;

	private Point ultimoPunto;

	private Double valorTarifa;

	private TipoLineaDTO tipoLinea;

	private Long tiempoEstimado;

	private ModoRecorrido modoLinea;

	private char activo;

	private List<HorarioLineaMetroDTO> horarioLineaMetro;

	private List<FrecuenciaLineaMetroDTO> frecuenciaLineaMetro;

	private List<EstacionLineaMetroDTO> estacionLineaMetro;
	
	private List<InfoViajesLineaDTO> infoViajesLineaMetro;

	@Override
	public LineaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public LineaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public LineaMetroDTO setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
		return this;
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

	public LineString getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(LineString coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Point getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(Point primerPunto) {
		this.primerPunto = primerPunto;
	}

	public Point getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(Point ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public ModoRecorrido getModoLinea() {
		return modoLinea;
	}

	public void setModoLinea(ModoRecorrido  modoLinea) {
		this.modoLinea = modoLinea;
	}

	public char getActivo() {
		return activo;
	}

	public void setActivo(char activo) {
		this.activo = activo;
	}

	public TipoLineaDTO getTipoLinea() {
		return tipoLinea;
	}

	public void setTipoLinea(TipoLineaDTO tipoLinea) {
		this.tipoLinea = tipoLinea;
	}

	public Long getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(Long tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public List<HorarioLineaMetroDTO> getHorarioLineaMetro() {
		if (Utils.isNull(horarioLineaMetro))
			return new ArrayList<>();
		return horarioLineaMetro;
	}

	public void setHorarioLineaMetro(List<HorarioLineaMetroDTO> horarioLineaMetro) {
		this.horarioLineaMetro = horarioLineaMetro;
	}

	public List<FrecuenciaLineaMetroDTO> getFrecuenciaLineaMetro() {
		if (Utils.isNull(frecuenciaLineaMetro))
			return new ArrayList<>();
		return frecuenciaLineaMetro;
	}

	public void setFrecuenciaLineaMetro(List<FrecuenciaLineaMetroDTO> frecuenciaLineaMetro) {
		this.frecuenciaLineaMetro = frecuenciaLineaMetro;
	}

	public List<EstacionLineaMetroDTO> getEstacionLineaMetro() {
		if (Utils.isNull(estacionLineaMetro))
			return new ArrayList<>();
		return estacionLineaMetro;
	}

	public void setEstacionLineaMetro(List<EstacionLineaMetroDTO> estacionLineaMetro) {
		this.estacionLineaMetro = estacionLineaMetro;
	}

	public List<InfoViajesLineaDTO> getInfoViajesLineaMetro() {
		return infoViajesLineaMetro;
	}

	public void setInfoViajesLineaMetro(List<InfoViajesLineaDTO> infoViajesLineaMetro) {
		this.infoViajesLineaMetro = infoViajesLineaMetro;
	}

}
