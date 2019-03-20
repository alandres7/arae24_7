package co.gov.metropol.area247.mapper.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CoordenadaUsuarioRequest {

	@ApiModelProperty(value = "Fecha de consumo del servicio web", required = true)
	private Date fecha;

	@ApiModelProperty(value = "Latitud ubicación origen", required = true)
	private double latitudOrigen;

	@ApiModelProperty(value = "Longitud ubicación origen", required = true)
	private double longitudOrigen;

	@ApiModelProperty(value = "Lista de tipos de transporte", required = true)
	private List<Integer> modosTransporte;

	@ApiModelProperty(value = "Longitud ubicación origen", required = true)
	private float radioAccion;

	public CoordenadaUsuarioRequest(Date fecha, double latitudOrigen, double longitudOrigen,
			List<Integer> modosTransporte, float radio) {
		this.fecha = fecha;
		this.latitudOrigen = latitudOrigen;
		this.longitudOrigen = longitudOrigen;
		this.modosTransporte = modosTransporte;
		this.radioAccion = radio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

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

	public List<Integer> getModosTransporte() {
		return modosTransporte;
	}

	public void setModosTransporte(List<Integer> modosTransporte) {
		this.modosTransporte = modosTransporte;
	}

	public float getRadioAccion() {
		return radioAccion;
	}

	public void setRadioAccion(float radioAccion) {
		this.radioAccion = radioAccion;
	}

}
