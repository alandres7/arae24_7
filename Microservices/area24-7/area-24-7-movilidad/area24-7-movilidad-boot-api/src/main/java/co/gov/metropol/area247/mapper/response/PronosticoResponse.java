package co.gov.metropol.area247.mapper.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import co.gov.metropol.area247.model.PronosticoDTO;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PronosticoResponse {

	@ApiModelProperty(value = "Identificador del  estado del tiempo en la ubicación origen", required = true)
	private int idEstadoTiempoOrigen;

	@ApiModelProperty(value = "Descripción del estado del tiempo en la ubicación origen", required = true)
	private String descTiempoOrigen;

	@ApiModelProperty(value = "Identificador de la probabilidad de precipitación en la siguiente ventana de tiempo en la ubicación origen", required = true)
	private int idProbabilidadOrigen;

	@ApiModelProperty(value = "Descripción de la probabilidad de precipitación en la siguiente ventana de tiempo en la ubicación origen", required = true)
	private String descProbabilidadOrigen;

	@ApiModelProperty(value = "Hora de inicio de la ventana de tiempo asociada a la probabilidad de precipitación", required = true)
	private Date horaInicioVentanaOrigen;

	@ApiModelProperty(value = "Hora fin de la ventana de tiempo asociada a la probabilidad de precipitación", required = true)
	private Date horaFinVentanaOrigen;

	@ApiModelProperty(value = "Identificador del  estado del tiempo en la ubicación destino", required = true)
	private int idEstadoTiempoDestino;

	@ApiModelProperty(value = "Descripción del estado del tiempo en la ubicación destinoDescripción del estado del tiempo en la ubicación destino", required = true)
	private String descTiempoDestino;

	@ApiModelProperty(value = "Identificador de la probabilidad de precipitación en  la ubicación destino", required = true)
	private int idProbabilidadDestino;

	@ApiModelProperty(value = "Descripción de la probabilidad de precipitación en la ubicación destino", required = true)
	private String descProbabilidadDestino;

	@ApiModelProperty(value = "Hora de inicio de la ventana de tiempo asociada a la probabilidad de precipitación", required = true)
	private Date horaInicioVentanaDestino;

	@ApiModelProperty(value = "Hora fin de la ventana de tiempo asociada a la probabilidad de precipitación", required = true)
	private Date horaFinVentanaDestino;

	@ApiModelProperty(value = "Codigo de la respuesta del servicio", required = true)
	private int codigo;

	@ApiModelProperty(value = "Descripcion de la respuesta del servicio", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Icono tiempo de origen", required = true)
	private String urlIconoTiempoOrigen;

	@ApiModelProperty(value = "Icono tiempo de destino", required = true)
	private String urlIconoTiempoDestino;

	@ApiModelProperty(value = "Icono probabilidad tiempo de origen", required = true)
	private String urlIconoProbabilidadOrigen;

	@ApiModelProperty(value = "Icono probabilidad tiempo destino", required = true)
	private String urlIconoProbabilidadDestino;

	public PronosticoResponse(PronosticoDTO pronosticoDTO) {
		this.idEstadoTiempoOrigen = pronosticoDTO.getIdEstadoTiempoDestino();
		this.descTiempoOrigen = pronosticoDTO.getDescTiempoOrigen();
		this.idProbabilidadOrigen = pronosticoDTO.getIdProbabilidadOrigen();
		this.descProbabilidadOrigen = pronosticoDTO.getDescProbabilidadOrigen();
		this.horaInicioVentanaOrigen = pronosticoDTO.getHoraInicioVentanaOrigen();
		this.horaFinVentanaOrigen = pronosticoDTO.getHoraFinVentanaOrigen();
		this.idEstadoTiempoDestino = pronosticoDTO.getIdEstadoTiempoDestino();
		this.descTiempoDestino = pronosticoDTO.getDescTiempoDestino();
		this.idProbabilidadDestino = pronosticoDTO.getIdProbabilidadDestino();
		this.descProbabilidadDestino = pronosticoDTO.getDescProbabilidadDestino();
		this.horaInicioVentanaDestino = pronosticoDTO.getHoraInicioVentanaDestino();
		this.horaFinVentanaDestino = pronosticoDTO.getHoraFinVentanaDestino();
		this.codigo = pronosticoDTO.getCodigo();
		this.descripcion = pronosticoDTO.getDescripcion();
		this.urlIconoTiempoOrigen = pronosticoDTO.getUrlIconoTiempoOrigen();
		this.urlIconoTiempoDestino = pronosticoDTO.getUrlIconoTiempoDestino();
		this.urlIconoProbabilidadOrigen = pronosticoDTO.getUrlIconoProbabilidadOrigen();
		this.urlIconoProbabilidadDestino = pronosticoDTO.getUrlIconoProbabilidadDestino();
	}

	public PronosticoResponse() {
		super();
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
