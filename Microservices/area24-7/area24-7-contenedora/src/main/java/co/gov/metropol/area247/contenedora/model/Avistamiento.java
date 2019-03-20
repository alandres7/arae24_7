package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.contenedora.model.Marcador;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "T247AVI_AVISTAMIENTO", schema="AVISTAM")
public class Avistamiento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AVISTAM.SEQ_AVISTAMIENTO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	
    @Column(name = "ID_MARCADOR")
    private Long idMarcador;
    
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMarcador() {
		return idMarcador;
	}

	public void setIdMarcador(Long idMarcador) {
		this.idMarcador = idMarcador;
	}   
    
    												
}
