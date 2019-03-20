package co.gov.metropol.area247.repository.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "T247AUD_LOG_EVENTOS", schema = "CONTENEDOR")
public class LogEvento {

	@Id
	@SequenceGenerator(name="SEQ_LOG_EVENTOS_ID", sequenceName="SEQ_LOG_EVENTOS_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LOG_EVENTOS_ID")
	@Column(name = "ID")
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreatedDate
	@Column(name = "D_FECHA_HORA", nullable = false)
	private Date fechaRegistro;

	@Column(name = "S_ENTIDAD_ORIGEN", nullable = false, length = 200)
	private String entidadOrigen;

	@Column(name = "S_ENTIDAD_DESTINO", nullable = false, length = 200)
	private String entidadDestino;

	@Column(name = "S_RESPONSE", nullable = false, length = 200)
	private String respuesta;

	@Column(name = "S_REQUEST", nullable = false, length = 50)
	private String peticion;

	@Column(name = "S_ERROR")
	private String error;

	@Column(name = "ID_APLICACION")
	private String idAplicacion;

	@Column(name = "ID_USUARIO")
	private String idUsuario;

	public LogEvento() {
	}

	public LogEvento(String origen, String destino, String peticion, String respuesta, String error) {
		this.entidadOrigen = origen;
		this.entidadDestino = destino;
		this.peticion = peticion;
		this.respuesta = respuesta;
		this.error = error;
		this.fechaRegistro = Calendar.getInstance().getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEntidadOrigen() {
		return entidadOrigen;
	}

	public void setEntidadOrigen(String entidadOrigen) {
		this.entidadOrigen = entidadOrigen;
	}

	public String getEntidadDestino() {
		return entidadDestino;
	}

	public void setEntidadDestino(String entidadDestino) {
		this.entidadDestino = entidadDestino;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getPeticion() {
		return peticion;
	}

	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

}
