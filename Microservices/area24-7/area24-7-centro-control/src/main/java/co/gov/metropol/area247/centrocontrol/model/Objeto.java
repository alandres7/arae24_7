package co.gov.metropol.area247.centrocontrol.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "T247SEG_OBJETO", schema = "CCONTROL")
public class Objeto implements Persistable<Long>{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_OBJETO_ID", allocationSize=1)
	private Long id;

	@Column(name = "ID_OBJETO_ENTIDAD")
	private Long idObjetoEntidad;
	
    @Column(name = "S_NOMBRE")
    private String nombre;
    
    @Column(name = "S_DESCRIPCION")
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_OBJETO", referencedColumnName = "ID")
    private TipoObjeto tipoObjeto;

    @Column(name = "ID_APLICACION")
    private Long idAplicacion;
    
              
    @Column(name = "N_PUEDE_ADICIONAR")
    private boolean puedeAdicionar;
    
    @Column(name = "N_PUEDE_EDITAR")
    private boolean puedeEditar;
    
    @Column(name = "N_PUEDE_BORRAR")
    private boolean puedeBorrar;
    
    @Column(name = "N_PUEDE_IMPRIMIR")
    private boolean puedeImprimir;
    
    @Column(name = "N_PUEDE_CONSULTAR")
    private boolean puedeConsultar;
    
       
    @Column(name = "N_AUDITAR_ADICION")
    private boolean auditarAdicion;
    
    @Column(name = "N_AUDITAR_EDICION")
    private boolean auditarEdicion;
    
    @Column(name = "N_AUDITAR_BORRAR")
    private boolean auditarBorrar;
    
    @Column(name = "N_AUDITAR_IMPRIMIR")
    private boolean auditarImprimir;
    
    @Column(name = "N_AUDITAR_CONSULTA")
    private boolean auditarConsulta;

       
    @Column(name = "N_PUEDE_AUDITAR_ADICION")
    private boolean puedeAuditarAdicion;
    
    @Column(name = "N_PUEDE_AUDITAR_EDICION")
    private boolean puedeAuditarEdicion;
    
    @Column(name = "N_PUEDE_AUDITAR_BORRAR")
    private boolean puedeAuditarBorrar;
    
    @Column(name = "N_PUEDE_AUDITAR_IMPRIMIR")
    private boolean puedeAuditarImprimir;
    
    @Column(name = "N_PUEDE_AUDITAR_CONSULTA")
    private boolean puedeAuditarConsulta;
    
          
    @Column(name = "N_ACTIVO")
    private boolean activo;
    
    public Objeto() {
    	
    }
        
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	@Override
	public String toString() {
		return "FormatoCarga [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
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

	public boolean isAuditarAdicion() {
		return auditarAdicion;
	}

	public void setAuditarAdicion(boolean auditarAdicion) {
		this.auditarAdicion = auditarAdicion;
	}

	public boolean isAuditarEdicion() {
		return auditarEdicion;
	}

	public void setAuditarEdicion(boolean auditarEdicion) {
		this.auditarEdicion = auditarEdicion;
	}

	public boolean isAuditarBorrar() {
		return auditarBorrar;
	}

	public void setAuditarBorrar(boolean auditarBorrar) {
		this.auditarBorrar = auditarBorrar;
	}

	public boolean isAuditarImprimir() {
		return auditarImprimir;
	}

	public void setAuditarImprimir(boolean auditarImprimir) {
		this.auditarImprimir = auditarImprimir;
	}

	public boolean isAuditarConsulta() {
		return auditarConsulta;
	}

	public void setAuditarConsulta(boolean auditarConsulta) {
		this.auditarConsulta = auditarConsulta;
	}
		
	public boolean isPuedeAuditarAdicion() {
		return puedeAuditarAdicion;
	}

	public void setPuedeAuditarAdicion(boolean puedeAuditarAdicion) {
		this.puedeAuditarAdicion = puedeAuditarAdicion;
	}

	public boolean isPuedeAuditarEdicion() {
		return puedeAuditarEdicion;
	}

	public void setPuedeAuditarEdicion(boolean puedeAuditarEdicion) {
		this.puedeAuditarEdicion = puedeAuditarEdicion;
	}

	public boolean isPuedeAuditarBorrar() {
		return puedeAuditarBorrar;
	}

	public void setPuedeAuditarBorrar(boolean puedeAuditarBorrar) {
		this.puedeAuditarBorrar = puedeAuditarBorrar;
	}

	public boolean isPuedeAuditarImprimir() {
		return puedeAuditarImprimir;
	}

	public void setPuedeAuditarImprimir(boolean puedeAuditarImprimir) {
		this.puedeAuditarImprimir = puedeAuditarImprimir;
	}

	public boolean isPuedeAuditarConsulta() {
		return puedeAuditarConsulta;
	}

	public void setPuedeAuditarConsulta(boolean puedeAuditarConsulta) {
		this.puedeAuditarConsulta = puedeAuditarConsulta;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public TipoObjeto getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(TipoObjeto tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public Long getIdObjetoEntidad() {
		return idObjetoEntidad;
	}

	public void setIdObjetoEntidad(Long idObjetoEntidad) {
		this.idObjetoEntidad = idObjetoEntidad;
	}

	@Override
	public boolean isNew() {
		return (getId()==null);
	}
			    
}