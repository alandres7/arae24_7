package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "D247VIA_TIPO_PARAMETRO", schema = "MOVILIDAD")
public class TipoParametro extends Entities {

	private static final long serialVersionUID = 762363041587865454L;

	@Column(name = "N_ORDEN")
	private Long orden;

	@Column(name = "S_NOMBRE")
	private String nombre;

	@Column(name = "S_VALOR")
	private String valor;

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public TipoParametro withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoParametro withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
