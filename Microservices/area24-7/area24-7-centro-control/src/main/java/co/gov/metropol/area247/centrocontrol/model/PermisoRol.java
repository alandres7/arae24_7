package co.gov.metropol.area247.centrocontrol.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T247SEG_PERMISO_ROL", schema = "CCONTROL")
public class PermisoRol{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	private Long id;
	
	@Column(name = "ID_ROL")
	private Long idRol;
	
	@Column(name = "ID_OBJETO")
	private Long idObjeto;
	
	@Column(name = "S_PUEDE_ADICIONAR")
	private Boolean puedeAdicionar;
	
	@Column(name = "S_PUEDE_EDITAR")
	private Boolean puedeEditar;
	
	@Column(name = "S_PUEDE_BORRAR")
	private Boolean puedeBorrar;
	
	@Column(name = "S_PUEDE_IMPRIMIR")
	private Boolean puedeImprimir;
	
	@Column(name = "S_PUEDE_CONSULTAR")
	private Boolean puedeConsultar;
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public Long getIdObjeto() {
		return idObjeto;
	}
	
	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}
		
	public Boolean getPuedeAdicionar() {
		return puedeAdicionar;
	}
	
	public void setPuedeAdicionar(Boolean puedeAdicionar) {
		this.puedeAdicionar = puedeAdicionar;
	}
	
	public Boolean getPuedeEditar() {
		return puedeEditar;
	}
	public void setPuedeEditar(Boolean puedeEditar) {
		this.puedeEditar = puedeEditar;
	}
	
	public Boolean getPuedeBorrar() {
		return puedeBorrar;
	}
	
	public void setPuedeBorrar(Boolean puedeBorrar) {
		this.puedeBorrar = puedeBorrar;
	}
	
	public Boolean getPuedeImprimir() {
		return puedeImprimir;
	}
	
	public void setPuedeImprimir(Boolean puedeImprimir) {
		this.puedeImprimir = puedeImprimir;
	}
	
	public Boolean getPuedeConsultar() {
		return puedeConsultar;
	}
	
	public void setPuedeConsultar(Boolean puedeConsultar) {
		this.puedeConsultar = puedeConsultar;
	}
	
}
