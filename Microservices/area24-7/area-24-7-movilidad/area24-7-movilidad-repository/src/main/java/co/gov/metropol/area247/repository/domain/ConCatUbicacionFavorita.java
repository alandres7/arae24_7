package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247CON_CAT_DIR_FAVORITA", schema = "CONTENEDOR")
public class ConCatUbicacionFavorita extends Entities {

	private static final long serialVersionUID = -1585665731066271972L;

	@Column(name = "CODIGO", nullable = false)
	private String codigo;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	@Override
	public ConCatUbicacionFavorita withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ConCatUbicacionFavorita withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
