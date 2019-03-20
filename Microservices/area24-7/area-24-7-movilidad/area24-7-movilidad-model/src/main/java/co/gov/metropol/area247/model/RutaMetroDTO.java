package co.gov.metropol.area247.model;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.Utils;

public class RutaMetroDTO extends AbstractDTO {

	private Long idItem;

	private String codigo;

	private String descripcion;

	private Double longitudRuta;

	private LineString coordenadas;

	private Point primerPunto;

	private Point ultimoPunto;

	private Long tiempoEstimado;

	private ModoRecorrido modoRutaDTO;

	private TipoRutaDTO tipoRutaDTO;

	private String rutaActiva;

	private int fuenteDatos;
	
	private Double valorTarifa;
	
	private List<FrecuenciaRutaMetroDTO> frecuenciasDTO;
	
	private List<HorarioRutaMetroDTO> horariosDTO;
	
	private List<EmpresaTransporteDTO> empresasTransporteDTO;
	
	private List<ParaderoRutaMetroDTO> paraderosDTO;
	
	private List<InfoViajesRutaDTO> infoViajesDTO;

	@Override
	public RutaMetroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public RutaMetroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

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

	public ModoRecorrido getModoRutaDTO() {
		return modoRutaDTO;
	}

	public void setModoRutaDTO(ModoRecorrido modoRutaDTO) {
		this.modoRutaDTO = modoRutaDTO;
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

	public TipoRutaDTO getTipoRutaDTO() {
		return tipoRutaDTO;
	}

	public void setTipoRutaDTO(TipoRutaDTO tipoRutaDTO) {
		this.tipoRutaDTO = tipoRutaDTO;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public List<FrecuenciaRutaMetroDTO> getFrecuenciasDTO() {
		if (Utils.isNull(frecuenciasDTO))
			return new ArrayList<>();
		return frecuenciasDTO;
	}

	public void setFrecuenciasDTO(List<FrecuenciaRutaMetroDTO> frecuenciasDTO) {
		this.frecuenciasDTO = frecuenciasDTO;
	}

	public List<HorarioRutaMetroDTO> getHorariosDTO() {
		if (Utils.isNull(horariosDTO))
			return new ArrayList<>();
		return horariosDTO;
	}

	public void setHorariosDTO(List<HorarioRutaMetroDTO> horariosDTO) {
		this.horariosDTO = horariosDTO;
	}

	public List<EmpresaTransporteDTO> getEmpresasTransporteDTO() {
		if (Utils.isNull(empresasTransporteDTO))
			return new ArrayList<>();
		return empresasTransporteDTO;
	}

	public void setEmpresasTransporteDTO(List<EmpresaTransporteDTO> empresasTransporteDTO) {
		this.empresasTransporteDTO = empresasTransporteDTO;
	}

	public List<ParaderoRutaMetroDTO> getParaderosDTO() {
		if (Utils.isNull(paraderosDTO))
			return new ArrayList<>();
		return paraderosDTO;
	}

	public void setParaderosDTO(List<ParaderoRutaMetroDTO> paraderosDTO) {
		this.paraderosDTO = paraderosDTO;
	}

	public List<InfoViajesRutaDTO> getInfoViajesDTO() {
		return infoViajesDTO;
	}

	public void setInfoViajesDTO(List<InfoViajesRutaDTO> infoViajesDTO) {
		this.infoViajesDTO = infoViajesDTO;
	}

}
