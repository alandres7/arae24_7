package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "D247_TARIFA_INTEGRADA_METRO", schema = "MOVILIDAD")
public class TarifaIntegradaMetro extends Entities {

	private static final long serialVersionUID = 5832589557936614199L;

	/**
	 * Identificador del primer modo de transporte de la combinacion
	 */
	@Column(name = "ID_TIPO_MODO_TRANSPORTE")
	private Long idModoTransporte1;
	
	/**
	 * Identificador del primer modo de transporte de la combinacion
	 */
	@Column(name = "ID_TIPO_MODO_TRANSPORTE_2")
	private Long idModoTransporte2;

	/**
	 * Identificador del primer modo de transporte de la combinacion
	 */
	@Column(name = "ID_TIPO_MODO_TRANSPORTE_3")
	private Long idModoTransporte3;

	/**
	 * Identificador del primer modo de transporte de la combinacion
	 */
	@Column(name = "ID_TIPO_MODO_TRANSPORTE_4")
	private Long idModoTransporte4;

	/**
	 * Identificador del primer modo de transporte de la combinacion
	 */
	@Column(name = "ID_TIPO_MODO_TRANSPORTE_5")
	private Long idModoTransporte5;

	/**
	 * Valor de la tarifa
	 */
	@Column(name = "N_VALOR_TARIFA")
	private Long valorTarifa;

	@SuppressWarnings("unchecked")
	@Override
	public TarifaIntegradaMetro withId(Long id) {
		super.setId(id);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TarifaIntegradaMetro withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdModoTransporte1() {
		return idModoTransporte1;
	}

	public void setIdModoTransporte1(Long idModoTransporte1) {
		this.idModoTransporte1 = idModoTransporte1;
	}

	public Long getIdModoTransporte2() {
		return idModoTransporte2;
	}

	public void setIdModoTransporte2(Long idModoTransporte2) {
		this.idModoTransporte2 = idModoTransporte2;
	}

	public Long getIdModoTransporte3() {
		return idModoTransporte3;
	}

	public void setIdModoTransporte3(Long idModoTransporte3) {
		this.idModoTransporte3 = idModoTransporte3;
	}

	public Long getIdModoTransporte4() {
		return idModoTransporte4;
	}

	public void setIdModoTransporte4(Long idModoTransporte4) {
		this.idModoTransporte4 = idModoTransporte4;
	}

	public Long getIdModoTransporte5() {
		return idModoTransporte5;
	}

	public void setIdModoTransporte5(Long idModoTransporte5) {
		this.idModoTransporte5 = idModoTransporte5;
	}

	public Long getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Long valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

}
