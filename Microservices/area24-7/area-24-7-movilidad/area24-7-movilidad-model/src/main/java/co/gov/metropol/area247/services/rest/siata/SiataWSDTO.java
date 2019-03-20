package co.gov.metropol.area247.services.rest.siata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class SiataWSDTO {

	@JsonProperty(value = "descTiempoDestino")
	private String descTiempoDestino;

	@JsonProperty(value = "horaFinVentanaOrigen")
	private Long horaFinVentanaOrigen;

	@JsonProperty(value = "idEstadoTiempoOrigen")
	private int idEstadoTiempoOrigen;

	@JsonProperty(value = "descTiempoOrigen")
	private String descTiempoOrigen;

	@JsonProperty(value = "idProbabilidadOrigen")
	private int idProbabilidadOrigen;

	@JsonProperty(value = "descProbabilidadOrigen")
	private String descProbabilidadOrigen;

	@JsonProperty(value = "horaInicioVentanaOrigen")
	private Long horaInicioVentanaOrigen;

	@JsonProperty(value = "idEstadoTiempoDestino")
	private int idEstadoTiempoDestino;

	@JsonProperty(value = "idProbabilidadDestino")
	private int idProbabilidadDestino;

	@JsonProperty(value = "descProbabilidadDestino")
	private String descProbabilidadDestino;

	@JsonProperty(value = "horaInicioVentanaDestino")
	private Long horaInicioVentanaDestino;

	@JsonProperty(value = "horaFinVentanaDestino")
	private Long horaFinVentanaDestino;

	@JsonProperty(value = "codigo")
	private int codigo;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "urlIconoTiempoOrigen")
	private String urlIconoTiempoOrigen;

	@JsonProperty(value = "urlIconoTiempoDestino")
	private String urlIconoTiempoDestino;

	@JsonProperty(value = "urlIconoProbabilidadOrigen")
	private String urlIconoProbabilidadOrigen;

	@JsonProperty(value = "urlIconoProbabilidadDestino")
	private String urlIconoProbabilidadDestino;

	public SiataWSDTO() {
		super();
	}

	public SiataWSDTO(@JsonProperty(value = "descTiempoDestino") String descTiempoDestino,
			@JsonProperty(value = "horaFinVentanaOrigen") Long horaFinVentanaOrigen,
			@JsonProperty(value = "idProbabilidadOrigen") int idProbabilidadOrigen,
			@JsonProperty(value = "descProbabilidadDestino") String descProbabilidadDestino,
			@JsonProperty(value = "descProbabilidadOrigen") String descProbabilidadOrigen,
			@JsonProperty(value = "descTiempoOrigen") String descTiempoOrigen,
			@JsonProperty(value = "idEstadoTiempoDestino") int idEstadoTiempoDestino,
			@JsonProperty(value = "horaFinVentanaDestino") Long horaFinVentanaDestino,
			@JsonProperty(value = "descripcion") String descripcion,
			@JsonProperty(value = "idProbabilidadDestino") int idProbabilidadDestino,
			@JsonProperty(value = "horaInicioVentanaOrigen") Long horaInicioVentanaOrigen,
			@JsonProperty(value = "horaInicioVentanaDestino") Long horaInicioVentanaDestino,
			@JsonProperty(value = "codigo") int codigo,
			@JsonProperty(value = "idEstadoTiempoOrigen") int idEstadoTiempoOrigen,
			@JsonProperty(value = "urlIconoTiempoOrigen") String urlIconoTiempoOrigen,
			@JsonProperty(value = "urlIconoTiempoDestino") String urlIconoTiempoDestino,
			@JsonProperty(value = "urlIconoProbabilidadOrigen") String urlIconoProbabilidadOrigen,
			@JsonProperty(value = "urlIconoProbabilidadDestino") String urlIconoProbabilidadDestino) {
		super();
		this.idEstadoTiempoOrigen = idEstadoTiempoOrigen;
		this.descTiempoOrigen = descTiempoOrigen;
		this.idProbabilidadOrigen = idProbabilidadOrigen;
		this.descProbabilidadOrigen = descProbabilidadOrigen;
		this.horaInicioVentanaOrigen = horaInicioVentanaOrigen;
		this.horaFinVentanaOrigen = horaFinVentanaOrigen;
		this.idEstadoTiempoDestino = idEstadoTiempoDestino;
		this.descTiempoDestino = descTiempoDestino;
		this.idProbabilidadDestino = idProbabilidadDestino;
		this.descProbabilidadDestino = descProbabilidadDestino;
		this.horaInicioVentanaDestino = horaInicioVentanaDestino;
		this.horaFinVentanaDestino = horaFinVentanaDestino;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.urlIconoTiempoOrigen = urlIconoTiempoOrigen;
		this.urlIconoTiempoDestino = urlIconoTiempoDestino;
		this.urlIconoProbabilidadOrigen = urlIconoProbabilidadOrigen;
		this.urlIconoProbabilidadDestino = urlIconoProbabilidadDestino;
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

	public Long getHoraInicioVentanaOrigen() {
		return horaInicioVentanaOrigen;
	}

	public void setHoraInicioVentanaOrigen(Long horaInicioVentanaOrigen) {
		this.horaInicioVentanaOrigen = horaInicioVentanaOrigen;
	}

	public Long getHoraFinVentanaOrigen() {
		return horaFinVentanaOrigen;
	}

	public void setHoraFinVentanaOrigen(Long horaFinVentanaOrigen) {
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

	public Long getHoraInicioVentanaDestino() {
		return horaInicioVentanaDestino;
	}

	public void setHoraInicioVentanaDestino(Long horaInicioVentanaDestino) {
		this.horaInicioVentanaDestino = horaInicioVentanaDestino;
	}

	public Long getHoraFinVentanaDestino() {
		return horaFinVentanaDestino;
	}

	public void setHoraFinVentanaDestino(Long horaFinVentanaDestino) {
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
