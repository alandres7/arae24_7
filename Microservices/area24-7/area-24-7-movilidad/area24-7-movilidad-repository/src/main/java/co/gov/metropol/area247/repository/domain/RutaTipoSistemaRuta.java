package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_RUTA_TIPO_SISTEMA_RUTA", schema = "MOVILIDAD")
public class RutaTipoSistemaRuta {
	
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_MOVILIDAD_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	@Column(name = "ID")
	protected Long id;
	
	@Column(name = "ID_TIPO_SISTEMA_RUTA")
	private Long idTipoSistemaRuta;

	@Column(name = "ID_RUTA")
	private Long idRuta;
	
	@Column(name = "S_ACTIVO")
	private String activo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RutaTipoSistemaRuta withId(Long id) {
		setId(id);
		return this;
	}

	public Long getIdTipoSistemaRuta() {
		return idTipoSistemaRuta;
	}

	public void setIdTipoSistemaRuta(Long idTipoSistemaRuta) {
		this.idTipoSistemaRuta = idTipoSistemaRuta;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	
	
}
