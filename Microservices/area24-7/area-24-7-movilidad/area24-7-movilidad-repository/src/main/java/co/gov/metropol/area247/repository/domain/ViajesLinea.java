package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

/**
 * Plantilla para manejar la informacion de los viajes que realiza una linea del
 * metro durante el tiempo especificado. Informacion como:
 * <ul>
 * <li>El tiempo que gasta un vehiculo en completar un viaje
 * <li>El numero de vehiculos que despachan
 * <li>La frecuencia de tiempo con que despachan cada vehiculo (segundos)
 * <li>Hora de inicio y fin en el que se cumple esta informacion
 * <li>Si el viaje es de ida y vuelta por las mismas calles (<code>true</code>
 * mismas calles, <code>false</code> caso contrario)
 * </ul>
 * <P>
 * <code>Creado 24/07/2018 11:44:00 a.m</code>
 */
@Entity
@Table(name = "T247VIA_VIAJES_LINEA", schema = "MOVILIDAD")
public class ViajesLinea extends Entities {

	private static final long serialVersionUID = -8794891531679734016L;

	@Column(name = "ID_LINEA")
	private Long idLinea;
	
	@Column(name = "N_DESPACHOS")
	private Long numSalidas;
	
	@Column(name = "N_FRECUENCIA")
	private Long frecuencia;
	
	@Column(name = "N_TIEMPO_VUELTA")
	private Long tiempoVuelta;
	
	@Column(name = "T_HORA_INICIO")
	private String horaInicio;
	
	@Column(name = "T_HORA_FIN")
	private String horaFin;
	
	@Column(name = "B_IDA_VUELTA")
	private boolean idaVuelta;

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	public Long getNumSalidas() {
		return numSalidas;
	}

	public void setNumSalidas(Long numSalidas) {
		this.numSalidas = numSalidas;
	}

	public Long getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Long frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Long getTiempoVuelta() {
		return tiempoVuelta;
	}

	public void setTiempoVuelta(Long tiempoVuelta) {
		this.tiempoVuelta = tiempoVuelta;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public boolean isIdaVuelta() {
		return idaVuelta;
	}

	public void setIdaVuelta(boolean idaVuelta) {
		this.idaVuelta = idaVuelta;
	}

	@Override
	public ViajesLinea withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ViajesLinea withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
