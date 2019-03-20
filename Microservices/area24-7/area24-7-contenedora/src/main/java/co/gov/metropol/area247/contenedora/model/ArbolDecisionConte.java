package co.gov.metropol.area247.contenedora.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity     
@Table(name = "T247CCN_ARBOL_DECISION", schema = "CCONTROL")
public class ArbolDecisionConte implements Persistable<Long>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_ARBOL_ID")
	@SequenceGenerator(name="CCONTROL.SEQ_ARBOL_ID", sequenceName="CCONTROL.SEQ_ARBOL_ID", allocationSize=1)
	private Long id;

	@Column(name = "S_NOMBRE")
    private String nombre;
    
    @Column(name = "S_DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ID_CAPA")
    private Long idCapa;
    
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;
    
    @Column(name = "N_ACTIVO")
    private boolean activo;
    
    
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
		
	public Long getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(Long idCapa) {
		this.idCapa = idCapa;
	}
		
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public boolean isNew() {
		return (getId()==null);
	}
	    
}