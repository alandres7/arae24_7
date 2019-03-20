package co.gov.metropol.area247.model;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.LineaMetro;

public class LineaDTO extends AbstractDTO {

	private Long idLinea;

	private String codigo;

	private String descripcion;

	private Double longitud;

	private LineString coordenadas;

	private Point primerPunto;

	private Point ultimoPunto;

	private char activo;
	
	private Long idTipoLinea;
	
	private Long idModoLinea;
	
	private Long tiempoEstimado;
	
	private Double valorTarifa;	

	
	public LineaDTO(LineaMetro lineaMetro) {
		this.idLinea = lineaMetro.getId();
		this.codigo = lineaMetro.getCodigo();
		this.descripcion = lineaMetro.getDescripcion();
		this.longitud = lineaMetro.getLongitud();
		this.coordenadas = lineaMetro.getCoordenadas();
		this.primerPunto = lineaMetro.getPrimerPunto();
		this.ultimoPunto = lineaMetro.getUltimoPunto();
		this.activo = lineaMetro.getActivo();
		this.idTipoLinea = lineaMetro.getIdTipoLinea();
//		this.idModoLinea = lineaMetro.getIdModoLinea();
		this.tiempoEstimado = lineaMetro.getTiempoEstimado();
//		this.valorTarifa = lineaMetro.getValorTarifa();
	}
	
	@Override
	public LineaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public LineaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
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

	public Long getModoLinea() {
		return idModoLinea;
	}

	public void setModoLinea(Long modoLinea) {
		this.idModoLinea = modoLinea;
	}

	public char getActivo() {
		return activo;
	}

	public void setActivo(char activo) {
		this.activo = activo;
	}

	public Long getTipoLinea() {
		return idTipoLinea;
	}

	public void setTipoLinea(Long tipoLinea) {
		this.idTipoLinea = tipoLinea;
	}

	public Long getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(Long tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

}
