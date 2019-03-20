package co.gov.metropol.area247.centrocontrol.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity     
@Table(name = "T247CCN_AUTORIDAD_MUNICIPIO", schema = "CCONTROL")
public class AutoridadMunicipio implements Persistable<Long>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CCONTROL.SEQ_AUTORIDAD_MUNICIPIO_ID")
	@SequenceGenerator(name="CCONTROL.SEQ_AUTORIDAD_MUNICIPIO_ID", sequenceName="CCONTROL.SEQ_AUTORIDAD_MUNICIPIO_ID", allocationSize=1)
	private Long id;

	@Column(name = "ID_NODO_ARBOL")
    private Long idNodoArbol;
    
    @Column(name = "S_MUNICIPIO")
    private String municipio;
    
    @Column(name = "ID_AUTORIDAD_COMPETENTE")
    private Long idAutoridadCompetente;

    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdNodoArbol() {
		return idNodoArbol;
	}

	public void setIdNodoArbol(Long idNodoArbol) {
		this.idNodoArbol = idNodoArbol;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Long getIdAutoridadCompetente() {
		return idAutoridadCompetente;
	}

	public void setIdAutoridadCompetente(Long idAutoridadCompetente) {
		this.idAutoridadCompetente = idAutoridadCompetente;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
	    
}