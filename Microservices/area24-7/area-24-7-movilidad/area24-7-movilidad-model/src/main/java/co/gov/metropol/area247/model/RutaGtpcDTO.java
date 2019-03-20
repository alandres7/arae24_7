package co.gov.metropol.area247.model;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;


public class RutaGtpcDTO extends AbstractDTO{

	/**
	 * Identificador unico de la ruta
	 * */
	@Loggable
	private Long idItem;
	
	/**
	 * Identificador unico de la fuente de datos
	 * */
	@Loggable
	private int fuenteDatos;
	
	/**
	 *Código de la ruta
	 * */
	@Loggable
	private String codigo;
	
	/**
	 * Descripción de la ruta
	 * */
	@Loggable
	private String descripcion;
	
	/**
	 * Longitud de la ruta
	 * */
	@Loggable
	private Double longitudRuta;
	
	/**
	 * Coordenadas de la ruta
	 * */
	@Loggable
	private LineString coordenadas;
	
	/**
	 * Punto inicio de la ruta
	 * */
	@Loggable
	private Point primerPunto;
	
	/**
	 * Punto fin de la ruta
	 * */
	@Loggable
	private Point ultimoPunto;
	
	/**
	 * Ruta activa
	 * */
	@Loggable
	private String rutaActiva;
	
	/**
	 * Tipo de la ruta
	 * */
	@Loggable
	private Long idTipoRuta;
	
	/**
	 * Tipo sistema ruta
	 * 
	@Loggable
	private Long idTipoSistemaRuta;
	*/
	
	/**
	 * Tiempo estimado de la ruta
	 * */
	@Loggable
	private Long  tiempoEstimado;
	
	/**
	 * Modo de la ruta
	 * */
	@Loggable
	private ModoRecorrido idModoRuta;
	
	/**
	 * Valor tarifa
	 * */
	@Loggable
	private Double  valorTarifa;


	@Override
	public RutaGtpcDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public RutaGtpcDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}



	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
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

	public Double getLongitudRuta() {
		return longitudRuta;
	}

	public void setLongitudRuta(Double longitudRuta) {
		this.longitudRuta = longitudRuta;
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

	public String getRutaActiva() {
		return rutaActiva;
	}

	public void setRutaActiva(String rutaActiva) {
		this.rutaActiva = rutaActiva;
	}

	public Long getIdTipoRuta() {
		return idTipoRuta;
	}

	public void setIdTipoRuta(Long idTipoRuta) {
		this.idTipoRuta = idTipoRuta;
	}

	/*public Long getIdTipoSistemaRuta() {
		return idTipoSistemaRuta;
	}

	public void setIdTipoSistemaRuta(Long idTipoSistemaRuta) {
		this.idTipoSistemaRuta = idTipoSistemaRuta;
	}*/


	public Long getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(Long tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public ModoRecorrido getIdModoRuta() {
		return idModoRuta;
	}

	public void setIdModoRuta(ModoRecorrido idModoRuta) {
		this.idModoRuta = idModoRuta;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

}
