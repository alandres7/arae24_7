package co.gov.metropol.area247.model;

import java.util.Date;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class PronosticoDTO extends AbstractDTO {

	@Loggable
	private int idEstadoTiempoOrigen;

	@Loggable
	private String descTiempoOrigen;

	@Loggable
	private int idProbabilidadOrigen;

	@Loggable
	private String descProbabilidadOrigen;

	@Loggable
	private Date horaInicioVentanaOrigen;

	@Loggable
	private Date horaFinVentanaOrigen;

	@Loggable
	private int idEstadoTiempoDestino;

	@Loggable
	private String descTiempoDestino;

	@Loggable
	private int idProbabilidadDestino;

	@Loggable
	private String descProbabilidadDestino;

	@Loggable
	private Date horaInicioVentanaDestino;

	@Loggable
	private Date horaFinVentanaDestino;

	@Loggable
	private int codigo;

	@Loggable
	private String descripcion;
	
	@Loggable
	private String urlIconoTiempoOrigen;
	
	@Loggable
	private String urlIconoTiempoDestino;
	
	@Loggable
	private String urlIconoProbabilidadOrigen;
	
	@Loggable
	private String urlIconoProbabilidadDestino;

	@Override
	public PronosticoDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public PronosticoDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public int getIdEstadoTiempoOrigen() {
		return idEstadoTiempoOrigen;
	}

	public void setIdEstadoTiempoOrigen(int idEstadoTiempoOrigen) {
		this.idEstadoTiempoOrigen = idEstadoTiempoOrigen;
	}

	public String getDescTiempoOrigen() {
		return descTiempoOrigen;
	}

	public void setDescTiempoOrigen(String descTiempoOrigen) {
		this.descTiempoOrigen = descTiempoOrigen;
	}

	public int getIdProbabilidadOrigen() {
		return idProbabilidadOrigen;
	}

	public void setIdProbabilidadOrigen(int idProbabilidadOrigen) {
		this.idProbabilidadOrigen = idProbabilidadOrigen;
	}

	public String getDescProbabilidadOrigen() {
		return descProbabilidadOrigen;
	}

	public void setDescProbabilidadOrigen(String descProbabilidadOrigen) {
		this.descProbabilidadOrigen = descProbabilidadOrigen;
	}

	public Date getHoraInicioVentanaOrigen() {
		return horaInicioVentanaOrigen;
	}

	public void setHoraInicioVentanaOrigen(Date horaInicioVentanaOrigen) {
		this.horaInicioVentanaOrigen = horaInicioVentanaOrigen;
	}

	public Date getHoraFinVentanaOrigen() {
		return horaFinVentanaOrigen;
	}

	public void setHoraFinVentanaOrigen(Date horaFinVentanaOrigen) {
		this.horaFinVentanaOrigen = horaFinVentanaOrigen;
	}

	public int getIdEstadoTiempoDestino() {
		return idEstadoTiempoDestino;
	}

	public void setIdEstadoTiempoDestino(int idEstadoTiempoDestino) {
		this.idEstadoTiempoDestino = idEstadoTiempoDestino;
	}

	public String getDescTiempoDestino() {
		return descTiempoDestino;
	}

	public void setDescTiempoDestino(String descTiempoDestino) {
		this.descTiempoDestino = descTiempoDestino;
	}

	public int getIdProbabilidadDestino() {
		return idProbabilidadDestino;
	}

	public void setIdProbabilidadDestino(int idProbabilidadDestino) {
		this.idProbabilidadDestino = idProbabilidadDestino;
	}

	public String getDescProbabilidadDestino() {
		return descProbabilidadDestino;
	}

	public void setDescProbabilidadDestino(String descProbabilidadDestino) {
		this.descProbabilidadDestino = descProbabilidadDestino;
	}

	public Date getHoraInicioVentanaDestino() {
		return horaInicioVentanaDestino;
	}

	public void setHoraInicioVentanaDestino(Date horaInicioVentanaDestino) {
		this.horaInicioVentanaDestino = horaInicioVentanaDestino;
	}

	public Date getHoraFinVentanaDestino() {
		return horaFinVentanaDestino;
	}

	public void setHoraFinVentanaDestino(Date horaFinVentanaDestino) {
		this.horaFinVentanaDestino = horaFinVentanaDestino;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrlIconoTiempoOrigen() {
		return urlIconoTiempoOrigen;
	}

	public void setUrlIconoTiempoOrigen(String urlIconoTiempoOrigen) {
		this.urlIconoTiempoOrigen = urlIconoTiempoOrigen;
	}

	public String getUrlIconoTiempoDestino() {
		return urlIconoTiempoDestino;
	}

	public void setUrlIconoTiempoDestino(String urlIconoTiempoDestino) {
		this.urlIconoTiempoDestino = urlIconoTiempoDestino;
	}

	public String getUrlIconoProbabilidadOrigen() {
		return urlIconoProbabilidadOrigen;
	}

	public void setUrlIconoProbabilidadOrigen(String urlIconoProbabilidadOrigen) {
		this.urlIconoProbabilidadOrigen = urlIconoProbabilidadOrigen;
	}

	public String getUrlIconoProbabilidadDestino() {
		return urlIconoProbabilidadDestino;
	}

	public void setUrlIconoProbabilidadDestino(String urlIconoProbabilidadDestino) {
		this.urlIconoProbabilidadDestino = urlIconoProbabilidadDestino;
	}

}
