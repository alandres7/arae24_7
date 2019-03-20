package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;

@Entity
@Table(name = "T247VIA_RUTA", schema = "MOVILIDAD")
public class Ruta extends Entities{

	private static final long serialVersionUID = 3660409023448645538L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "N_FUENTE_DATOS")
	private int fuenteDatos;

	@Column(name = "S_CODIGO_RUTA")
	private String codigo;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "N_LONGITUD_RUTA")
	private Double longitudRuta;

	@Column(name = "S_COORDENADAS")
	private LineString coordenadas;

	@Column(name = "S_PRIMER_PUNTO")
	private Point primerPunto;

	@Column(name = "S_ULTIMO_PUNTO")
	private Point ultimoPunto;

	@Column(name = "S_ACTIVO")
	private String rutaActiva;

	@Column(name = "ID_TIPO_RUTA")
	private Long idTipoRuta;

	//@Column(name = "ID_TIPO_SISTEMA_RUTA")
	//private Long idTipoSistemaRuta;

	@Column(name = "N_TIEMPO_ESTIMADO_RUTA")
	private Long tiempoEstimado;

	@Column(name = "ID_MODO_RUTA")
	@Enumerated(EnumType.ORDINAL)
	private ModoRecorrido idModoRuta;

	@Column(name = "N_VALOR_TARIFA_RUTA")
	private Double valorTarifa;

	@Column(name = "B_ENABLED")
	protected boolean enabled = true;

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
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

	public Long getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(Long tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
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

	public ModoRecorrido getIdModoRuta() {
		return idModoRuta;
	}

	public void setIdModoRuta(ModoRecorrido idModoRuta) {
		this.idModoRuta = idModoRuta;
	}
	
	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	@Override
	public Ruta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public Ruta withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
