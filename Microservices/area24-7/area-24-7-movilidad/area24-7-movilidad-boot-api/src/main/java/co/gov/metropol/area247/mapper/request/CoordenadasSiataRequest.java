package co.gov.metropol.area247.mapper.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;

/**
 * Informacion de las coordenadas origen, destino y fecha de consulta que se le
 * envia al servicio de SIATA
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CoordenadasSiataRequest {

	@ApiModelProperty(value = "Latitud donde esta parado el usuario", required = true)
	private double latitudOrigen;

	@ApiModelProperty(value = "Longitud donde se encuentra parado el usuario", required = true)
	private double longitudOrigen;

	@ApiModelProperty(value = "Latitud donde desea ir el usuario", required = true)
	private double latitudDestino;

	@ApiModelProperty(value = "Longitud donde desea ir el usuario", required = true)
	private double longitudDestino;

	@ApiModelProperty(value = "fecha de consumo del servicio", required = true)
	private Date fecha;

	@ApiModelProperty(value = "fecha y hora de la consulta de las posibilidades de viaje", required = true)
	private Date fechaOrigen;

	@ApiModelProperty(value = "fecha y hora estimada de llegada al destino", required = true)
	private Date fechaDestino;

	public double getLatitudOrigen() {
		return latitudOrigen;
	}

	public void setLatitudOrigen(double latitudOrigen) {
		this.latitudOrigen = latitudOrigen;
	}

	public double getLongitudOrigen() {
		return longitudOrigen;
	}

	public void setLongitudOrigen(double longitudOrigen) {
		this.longitudOrigen = longitudOrigen;
	}

	public double getLatitudDestino() {
		return latitudDestino;
	}

	public void setLatitudDestino(double latitudDestino) {
		this.latitudDestino = latitudDestino;
	}

	public double getLongitudDestino() {
		return longitudDestino;
	}

	public void setLongitudDestino(double longitudDestino) {
		this.longitudDestino = longitudDestino;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaOrigen() {
		return fechaOrigen;
	}

	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}

	public Date getFechaDestino() {
		return fechaDestino;
	}

	public void setFechaDestino(Date fechaDestino) {
		this.fechaDestino = fechaDestino;
	}

}
