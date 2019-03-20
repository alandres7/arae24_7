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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import co.gov.metropol.area247.contenedora.model.Icono;

@Entity
@Table(name="T247AVI_ESPECIE", schema="AVISTAM")
public class Especie implements Serializable {

	/*
	CREATE SEQUENCE  "AVISTAM"."SEQ_ESPECIE_ID"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE;
	*/
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AVISTAM.SEQ_ESPECIE_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@NotNull
	@Size(max = 200)
	@Column(name = "S_NOMBRE")
	private String nombre;
		
	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;
	
	@Column(name = "ID_CATEGORIA")
	private Long idCategoria;
	
	
	
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

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

}
