package co.gov.metropol.area247.contenedora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "T247CCN_MENSAJE", schema = "CCONTROL")
public class Mensaje implements Persistable<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_MENSAJE_ID", allocationSize=1)
	private Long id;
	
	@Column(name = "S_CONTENIDO")
	private String descripcion;
	
	@Column(name = "S_TITULO")
	private String titulo;
	
	@Column(name = "S_NOMBRE_IDENTIFICADOR")
	private String nombreIdentificador;
	
	@Column(name = "S_USO")
	private String uso;
	
	//@ManyToOne()
	@Column(name = "ID_APLICACION")
	private Long idAplicacion;
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return(getId()==null);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String contenido) {
		this.descripcion = contenido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreIdentificador() {
		return nombreIdentificador;
	}

	public void setNombreIdentificador(String nombreIdentificador) {
		this.nombreIdentificador = nombreIdentificador;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
}
