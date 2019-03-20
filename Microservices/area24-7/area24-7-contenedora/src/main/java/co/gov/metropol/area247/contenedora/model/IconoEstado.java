package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Marcador;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "T247AVI_ICONO_ESTADO", schema="AVISTAM")
public class IconoEstado implements Serializable {

	/*
	CREATE SEQUENCE  "AVISTAM"."SEQ_ICONO_ESTADO_ID"  MINVALUE 2 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 2 CACHE 20 NOORDER  NOCYCLE ;
	*/
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AVISTAM.SEQ_ICONO_ESTADO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@Column(name = "ID_CAPA")
	private Long idCapa;
	
	@Column(name = "ID_CATEGORIA")
	private Long idCategoria;
	
	@Column(name = "ID_ESTADO")
	private int idEstado;
	
	@ManyToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;
	
 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	
	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}
											
}
