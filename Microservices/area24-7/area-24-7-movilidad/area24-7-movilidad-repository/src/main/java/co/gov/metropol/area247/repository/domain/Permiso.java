package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.Privileges;

/**
 * Created by andres on 5/26/17.
 */
@Entity
@Table(name = "TSIMAUD_PERMISOS", schema = "MOVILIDAD")
public class Permiso extends Entities {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
    @Column(name = "S_TIPO_PERMISO", nullable = false, length = 20)
    private Privileges privilege;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_AUTORIZACION", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private Autorizacion autorizacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private Recurso recurso;

    @Override
    public Permiso withId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public Permiso withEnabled(boolean enabled) {
        super.setEnabled(enabled);
        return this;
    }

    public Privileges getPrivilege() {
        return privilege;
    }

    public Permiso setPrivilege(Privileges privilege) {
        this.privilege = privilege;
        return this;
    }

	public Autorizacion getAutorizacion() {
		return autorizacion;
	}

	public Permiso setAutorizacion(Autorizacion autorizacion) {
		this.autorizacion = autorizacion;
		return this;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public Permiso setRecurso(Recurso recurso) {
		this.recurso = recurso;
		return this;
	}
}
