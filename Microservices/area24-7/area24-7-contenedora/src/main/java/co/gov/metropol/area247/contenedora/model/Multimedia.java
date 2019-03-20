package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.vigias.model.Vigia;

@Entity
@Table(name = "T247CON_MULTIMEDIA", schema = "CONTENEDOR")
public class Multimedia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	  @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_MULTIMEDIA_ID", allocationSize=1)
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	  private Long id;
	
	@NotNull
	@Column(name = "S_RUTA")
	private String ruta;
	
	@Size(max = 200)
	@Column(name = "S_NOMBRE")
	private String nombre;
	
//	@NotNull
//	@Column(name = "ID_APLICACION")
//	private Long idAplicacion;
//	
//	@Column(name = "ID_EVENTO")
//	private Long idEvento;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_MULTIMEDIA", referencedColumnName = "ID")
	private TipoMultimedia tipoMultimedia;
	
	@ManyToOne
	@JoinColumn(name = "ID_VIGIA", referencedColumnName = "ID")
	private Vigia vigia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

//	public Long getIdAplicacion() {
//		return idAplicacion;
//	}
//
//	public void setIdAplicacion(Long idAplicacion) {
//		this.idAplicacion = idAplicacion;
//	}
//
//	public Long getIdEvento() {
//		return idEvento;
//	}

//	public void setIdEvento(Long idEvento) {
//		this.idEvento = idEvento;
//	}

	public TipoMultimedia getTipoMultimedia() {
		return tipoMultimedia;
	}

	public void setTipoMultimedia(TipoMultimedia tipoMultimedia) {
		this.tipoMultimedia = tipoMultimedia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Vigia getVigia() {
		return vigia;
	}

	public void setVigia(Vigia vigia) {
		this.vigia = vigia;
	}
	
	

}
