package co.gov.metropol.area247.centrocontrol.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "T247SEG_PERMISO_USUARIO", schema = "CCONTROL" , uniqueConstraints={ 
		@UniqueConstraint(columnNames = {"ID_USUARIO", "ID_OBJETO"})}
)
public class PermisoUsuario implements Persistable<Long> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_PERMISO_USUARIO_ID", allocationSize=1)
	private Long id;
	
	@Column(name = "ID_USUARIO")
    private Long idUsuario;
	
	@Column(name = "ID_OBJETO")
    private Long idObjeto;
	
	@Column(name = "N_PUEDE_ADICIONAR")
    private boolean puedeAdicionar;
    
    @Column(name = "N_PUEDE_EDITAR")
    private boolean puedeEditar;
    
    @Column(name = "N_PUEDE_BORRAR")
    private boolean puedeBorrar;
    
    @Column(name = "N_PUEDE_IMPRIMIR")
    private boolean puedeImprimir;
    
    @Column(name="N_PUEDE_CONSULTAR")
    private boolean puedeConsultar;
    
    @Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public boolean isPuedeAdicionar() {
		return puedeAdicionar;
	}

	public void setPuedeAdicionar(boolean puedeAdicionar) {
		this.puedeAdicionar = puedeAdicionar;
	}

	public boolean isPuedeEditar() {
		return puedeEditar;
	}

	public void setPuedeEditar(boolean puedeEditar) {
		this.puedeEditar = puedeEditar;
	}

	public boolean isPuedeBorrar() {
		return puedeBorrar;
	}

	public void setPuedeBorrar(boolean puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}

	public boolean isPuedeImprimir() {
		return puedeImprimir;
	}

	public void setPuedeImprimir(boolean puedeImprimir) {
		this.puedeImprimir = puedeImprimir;
	}

	public boolean isPuedeConsultar() {
		return puedeConsultar;
	}

	public void setPuedeConsultar(boolean puedeConsultar) {
		this.puedeConsultar = puedeConsultar;
	}
	

	public Long getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}

	@Override
	public boolean isNew() {
		return (getId()==null);
	}
	
	
}
