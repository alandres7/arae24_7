package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "TSIMAUD_AUTORIZACIONES", schema = "MOVILIDAD")
public class Autorizacion extends Entities {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_NOMBRE_ROLE", unique = true, length = 20)
    private String role;

    @Lob
    @Column(name = "S_DESCRIPCION")
    private String descripcion;

    @Override
    public Autorizacion withId(Long id) {
		super.setId(id);
		return this;
    }

    @Override
    public Autorizacion withEnabled(boolean enabled) {
        super.setEnabled(enabled);
        return null;
    }

	public String getRole() {
		return role;
	}

	public Autorizacion setRole(String role) {
		this.role = role;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Autorizacion setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
}
