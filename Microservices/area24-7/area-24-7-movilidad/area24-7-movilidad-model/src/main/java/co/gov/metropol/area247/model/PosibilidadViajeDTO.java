package co.gov.metropol.area247.model;

import java.util.Date;
import java.util.List;

/**
 * Contiene las variables necesarias para manejar la informacion de los posibles
 * caminos que se pueden haber desde un origen hasta un destino segun el modo de
 * transporte.
 *
 */
public class PosibilidadViajeDTO {

	private Date fecha;

	/**
	 * Contiene la informacion de los viajes posibles que hay desde un punto
	 * inicio hasta un punto fin.
	 */
	private List<ViajeDTO> viajesDTO;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<ViajeDTO> getViajesDTO() {
		return viajesDTO;
	}

	public void setViajesDTO(List<ViajeDTO> viajesDTO) {
		this.viajesDTO = viajesDTO;
	}

}
