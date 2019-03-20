package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "D247CON_VENTANA_INFORMACION", schema = "CONTENEDOR")
public class VentanaInformacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_VENTANA_INFORMACION_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;

	@Column(name = "S_COLOR")
	private String color;

	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;

	@OneToOne
	@JoinColumn(name = "ID_MULTIMEDIA", referencedColumnName = "ID")
	private Multimedia multimedia;

	@Column(name = "S_TITULO")
	private String titulo;

	@Size(max = 100)
	@Column(name = "S_NOMBRE", unique = true)
	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENTIDAD")
	private List<Caracteristica> caracteristicas;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "S_SUBTITULO")
	private String subtitulo;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "T247CON_VENTANA_INFO_ICONO", joinColumns = @JoinColumn(name = "ID_VENTANA_INFO", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ICONO", referencedColumnName = "ID"))
	@SequenceGenerator(name = "SEQ_GEN_2", sequenceName = "SEQ_VENTANA_INFO_ICONO_ID", allocationSize = 1)
	@CollectionId(columns = @Column(name = "ID"), type = @Type(type = "long"), generator = "SEQ_GEN_2")
	private List<Icono> infograficos;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENTIDAD")
	private List<Caracteristica> textoInfograficos;

	@Column(name = "S_DESCRIPCION2")
	private String descripcion2;

	@Column(name = "S_ENLACE_WEB")
	private String enlaceWeb;

	@OneToMany(mappedBy = "ventanaInformacion")
	private List<Marcador> marcadores;

	public VentanaInformacion() {
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

	public String getEnlaceWeb() {
		return enlaceWeb;
	}

	public void setEnlaceWeb(String enlaceWeb) {
		this.enlaceWeb = enlaceWeb;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public List<Icono> getInfograficos() {
		return infograficos;
	}

	public void setInfograficos(List<Icono> infograficos) {
		this.infograficos = infograficos;
	}

	public String getDescripcion2() {
		return descripcion2;
	}

	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public void setTextoInfograficos(List<Caracteristica> textoInfograficos) {
		this.textoInfograficos = textoInfograficos;
	}

	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	public List<Caracteristica> getTextoInfograficos() {
		return textoInfograficos;
	}

	public Multimedia getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(Multimedia multimedia) {
		this.multimedia = multimedia;
	}

	public List<Marcador> getMarcadores() {
		return marcadores;
	}

	public void setMarcadores(List<Marcador> marcadores) {
		this.marcadores = marcadores;
	}

}
